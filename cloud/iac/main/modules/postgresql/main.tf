resource "random_string" "server_suffix" {
  count   = var.server_name == null ? 1 : 0
  length  = 6
  special = false
  upper   = false
}

resource "random_password" "admin_password" {
  count            = var.administrator_password == null ? 1 : 0
  length           = 20
  special          = true
  override_special = "!#$%&*()-_=+[]{}<>:?"
}

locals {
  server_name = coalesce(
    var.server_name,
    "${var.server_name_prefix}-${try(random_string.server_suffix[0].result, "demo")}"
  )
  admin_password = coalesce(
    var.administrator_password,
    try(random_password.admin_password[0].result, "")
  )
}

resource "azurerm_postgresql_flexible_server" "this" {
  name                          = local.server_name
  resource_group_name           = var.resource_group_name
  location                      = var.location
  version                       = var.postgres_version
  administrator_login           = var.administrator_login
  administrator_password        = local.admin_password
  sku_name                      = var.sku_name
  storage_mb                    = var.storage_mb
  public_network_access_enabled = true
  auto_grow_enabled             = false
  tags                          = var.tags

  lifecycle {
    ignore_changes = [
      zone,
      high_availability[0].standby_availability_zone
    ]
  }
}

resource "azurerm_postgresql_flexible_server_database" "this" {
  name      = var.database_name
  server_id = azurerm_postgresql_flexible_server.this.id
  collation = "en_US.utf8"
  charset   = "UTF8"
}

# Azure services firewall rule (0.0.0.0 is the official Azure mechanism for allowing Azure internal traffic)
resource "azurerm_postgresql_flexible_server_firewall_rule" "allow_azure_services" {
  count            = var.allow_azure_services ? 1 : 0
  name             = "AllowAllAzureServicesAndResourcesWithinAzureIps"
  server_id        = azurerm_postgresql_flexible_server.this.id
  start_ip_address = "0.0.0.0"
  end_ip_address   = "0.0.0.0"
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "custom" {
  for_each         = var.allowed_ip_addresses
  name             = each.key
  server_id        = azurerm_postgresql_flexible_server.this.id
  start_ip_address = each.value
  end_ip_address   = each.value
}
