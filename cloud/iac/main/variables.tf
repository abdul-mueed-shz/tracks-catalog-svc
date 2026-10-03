variable "project_name" {
  description = "Project name identifier used in resource naming"
  type        = string
  default     = "catalog-svc"
}

variable "environment" {
  description = "Deployment environment name (e.g. demo, dev, prod)"
  type        = string
  default     = "demo"
}

variable "location" {
  description = "Target Azure region (e.g. switzerlandnorth, polandcentral, italynorth)"
  type        = string
  default     = "switzerlandnorth"
}

variable "tags" {
  description = "Common tags applied to all Azure resources"
  type        = map(string)
  default = {
    Environment = "demo"
    Project     = "catalog-service"
    ManagedBy   = "Terragrunt"
  }
}

# PostgreSQL Variables
variable "postgres_version" {
  description = "PostgreSQL version"
  type        = string
  default     = "16"
}

variable "postgres_sku" {
  description = "Compute SKU for PostgreSQL Flexible Server"
  type        = string
  default     = "B_Standard_B1ms"
}

variable "postgres_storage_mb" {
  description = "Storage in MB for PostgreSQL"
  type        = number
  default     = 32768
}

variable "postgres_admin_login" {
  description = "Admin username for PostgreSQL"
  type        = string
  default     = "catalogadmin"
}

variable "postgres_admin_password" {
  description = "Admin password for PostgreSQL (auto-generated if null)"
  type        = string
  sensitive   = true
  default     = null
}

variable "postgres_database_name" {
  description = "Initial database name"
  type        = string
  default     = "catalog"
}

variable "allowed_ip_addresses" {
  description = "Map of additional client IPs to allow into PostgreSQL firewall { rule_name = ip_address }"
  type        = map(string)
  default     = {}
}

# Redis Variables
variable "redis_sku" {
  description = "Azure Cache for Redis SKU tier (Basic, Standard, Premium)"
  type        = string
  default     = "Basic"
}

variable "redis_family" {
  description = "Redis family (C for Basic/Standard)"
  type        = string
  default     = "C"
}

variable "redis_capacity" {
  description = "Redis capacity tier (0 is 250MB, minimum cost for demo)"
  type        = number
  default     = 0
}

variable "allow_all_redis_ips_for_demo" {
  description = "Whether to allow all IPs through Redis firewall for demo convenience"
  type        = bool
  default     = true
}

variable "use_managed_redis" {
  description = "Whether to provision Azure Managed Redis. If false (default), runs a zero-cost Redis 7 container alongside the app (saves student credits and avoids retirement errors)."
  type        = bool
  default     = false
}

# Container App Variables
variable "container_image" {
  description = "Docker image for catalog-service"
  type        = string
  default     = "ghcr.io/abdul-mueed-shz/tracks-catalog-svc:latest"
}

variable "container_cpu" {
  description = "CPU core allocation for the container"
  type        = number
  default     = 0.5
}

variable "container_memory" {
  description = "Memory allocation for the container"
  type        = string
  default     = "1.0Gi"
}

variable "min_replicas" {
  description = "Minimum replicas (0 for scale-to-zero, 1 for continuous demo availability)"
  type        = number
  default     = 1
}

variable "max_replicas" {
  description = "Maximum replicas"
  type        = number
  default     = 2
}

variable "extra_env_vars" {
  description = "Additional environment variables to inject into container"
  type        = map(string)
  default     = {}
}

# ACR Variables
variable "enable_acr" {
  description = "Whether to provision an Azure Container Registry (Basic) for hosting private images"
  type        = bool
  default     = false
}
