resource "random_string" "redis_suffix" {
  count   = var.redis_name == null ? 1 : 0
  length  = 6
  special = false
  upper   = false
}

locals {
  redis_name = coalesce(
    var.redis_name,
    "${var.redis_name_prefix}-${try(random_string.redis_suffix[0].result, "demo")}"
  )
}

resource "azurerm_redis_cache" "this" {
  name                          = local.redis_name
  location                      = var.location
  resource_group_name           = var.resource_group_name
  capacity                      = var.capacity
  family                        = var.family
  sku_name                      = var.sku_name
  non_ssl_port_enabled          = var.enable_non_ssl_port
  minimum_tls_version           = "1.2"
  public_network_access_enabled = true
  tags                          = var.tags

  redis_configuration {
    maxmemory_policy = "allkeys-lru"
  }
}

resource "azurerm_redis_firewall_rule" "allow_all" {
  count               = var.allow_all_ips_for_demo ? 1 : 0
  name                = "allow_all_demo"
  redis_cache_name    = azurerm_redis_cache.this.name
  resource_group_name = var.resource_group_name
  start_ip            = "0.0.0.0"
  end_ip              = "255.255.255.255"
}
