variable "resource_group_name" {
  description = "Name of the Azure Resource Group"
  type        = string
}

variable "location" {
  description = "Azure region to deploy resources (e.g. westeurope, eastus)"
  type        = string
}

variable "log_analytics_workspace_name" {
  description = "Name of the Log Analytics Workspace for Container Apps telemetry"
  type        = string
  default     = null
}

variable "log_retention_days" {
  description = "Data retention period in days for Log Analytics"
  type        = number
  default     = 30
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
  default     = {}
}
