resource "random_string" "acr_suffix" {
  count   = var.acr_name == null ? 1 : 0
  length  = 6
  special = false
  upper   = false
}

locals {
  acr_name = coalesce(
    var.acr_name,
    "${var.acr_name_prefix}${try(random_string.acr_suffix[0].result, "demo")}"
  )
}

resource "azurerm_container_registry" "this" {
  name                = local.acr_name
  resource_group_name = var.resource_group_name
  location            = var.location
  sku                 = var.sku
  admin_enabled       = var.admin_enabled
  tags                = var.tags
}
