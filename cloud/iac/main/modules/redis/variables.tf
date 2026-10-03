variable "redis_name" {
  description = "Name of the Azure Redis Cache (must be globally unique across Azure)"
  type        = string
  default     = null
}

variable "redis_name_prefix" {
  description = "Prefix for the Redis instance name if redis_name is not provided"
  type        = string
  default     = "redis-catalog"
}

variable "resource_group_name" {
  description = "Name of the Resource Group"
  type        = string
}

variable "location" {
  description = "Azure region"
  type        = string
}

variable "sku_name" {
  description = "Redis SKU tier (Basic, Standard, Premium)"
  type        = string
  default     = "Basic"
}

variable "family" {
  description = "Redis family (C for Basic/Standard, P for Premium)"
  type        = string
  default     = "C"
}

variable "capacity" {
  description = "Redis capacity tier (0 is 250MB, ideal for demos)"
  type        = number
  default     = 0
}

variable "enable_non_ssl_port" {
  description = "Whether to enable non-SSL port 6379"
  type        = bool
  default     = false
}

variable "allow_all_ips_for_demo" {
  description = "Whether to add firewall rule allowing all public IPs (0.0.0.0-255.255.255.255) for demo simplicity"
  type        = bool
  default     = true
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
  default     = {}
}
