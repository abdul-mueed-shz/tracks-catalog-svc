variable "server_name" {
  description = "Name of the PostgreSQL Flexible Server (must be globally unique across Azure)"
  type        = string
  default     = null
}

variable "server_name_prefix" {
  description = "Prefix for the server name if server_name is not provided"
  type        = string
  default     = "psql-catalog"
}

variable "resource_group_name" {
  description = "Name of the existing Resource Group"
  type        = string
}

variable "location" {
  description = "Azure region"
  type        = string
}

variable "postgres_version" {
  description = "PostgreSQL major version (16 is recommended for catalog-service)"
  type        = string
  default     = "16"
}

variable "administrator_login" {
  description = "Administrator login username"
  type        = string
  default     = "catalogadmin"
}

variable "administrator_password" {
  description = "Administrator password (will be auto-generated if null)"
  type        = string
  sensitive   = true
  default     = null
}

variable "sku_name" {
  description = "Compute SKU tier for PostgreSQL Flexible Server (e.g. B_Standard_B1ms for demo)"
  type        = string
  default     = "B_Standard_B1ms"
}

variable "storage_mb" {
  description = "Storage capacity in megabytes (32768 = 32 GB, minimum for Flexible Server)"
  type        = number
  default     = 32768
}

variable "database_name" {
  description = "Name of the initial application database"
  type        = string
  default     = "catalog"
}

variable "allow_azure_services" {
  description = "Allow traffic from all Azure services (e.g. Container Apps) via 0.0.0.0 firewall rule"
  type        = bool
  default     = true
}

variable "allowed_ip_addresses" {
  description = "Map of additional firewall rules { rule_name = ip_address } for developer/local access"
  type        = map(string)
  default     = {}
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
  default     = {}
}
