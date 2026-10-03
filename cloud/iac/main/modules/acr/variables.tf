variable "acr_name" {
  description = "Name of the Azure Container Registry (alphanumeric only, globally unique)"
  type        = string
  default     = null
}

variable "acr_name_prefix" {
  description = "Prefix for ACR name if acr_name is not provided"
  type        = string
  default     = "acrcatalog"
}

variable "resource_group_name" {
  description = "Name of the Resource Group"
  type        = string
}

variable "location" {
  description = "Azure region"
  type        = string
}

variable "sku" {
  description = "ACR SKU tier (Basic, Standard, Premium)"
  type        = string
  default     = "Basic"
}

variable "admin_enabled" {
  description = "Whether to enable admin user credentials"
  type        = bool
  default     = true
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
  default     = {}
}
