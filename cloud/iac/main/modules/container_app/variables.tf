variable "app_name" {
  description = "Name of the Container App"
  type        = string
}

variable "resource_group_name" {
  description = "Name of the Resource Group"
  type        = string
}

variable "location" {
  description = "Azure region"
  type        = string
}

variable "log_analytics_workspace_id" {
  description = "ID of the Log Analytics Workspace for Container Apps Environment"
  type        = string
}

variable "container_image" {
  description = "Container image to deploy (e.g. ghcr.io/username/catalog-service:latest or acr.azurecr.io/catalog-service:latest)"
  type        = string
}

variable "target_port" {
  description = "Target port inside container"
  type        = number
  default     = 8080
}

variable "cpu" {
  description = "CPU allocated to the container (e.g. 0.25, 0.5, 1.0)"
  type        = number
  default     = 0.5
}

variable "memory" {
  description = "Memory allocated to the container (e.g. 0.5Gi, 1.0Gi, 2.0Gi)"
  type        = string
  default     = "1.0Gi"
}

variable "min_replicas" {
  description = "Minimum number of container replicas (0 for scale-to-zero, 1 for continuous availability)"
  type        = number
  default     = 1
}

variable "max_replicas" {
  description = "Maximum number of container replicas"
  type        = number
  default     = 2
}

variable "datasource_url" {
  description = "Spring JDBC connection URL for PostgreSQL"
  type        = string
}

variable "datasource_username" {
  description = "PostgreSQL administrator/app username"
  type        = string
}

variable "datasource_password" {
  description = "PostgreSQL administrator/app password"
  type        = string
  sensitive   = true
}

variable "redis_host" {
  description = "Redis cache hostname"
  type        = string
  default     = "localhost"
}

variable "redis_port" {
  description = "Redis cache port"
  type        = number
  default     = 6379
}

variable "redis_password" {
  description = "Redis cache access key"
  type        = string
  sensitive   = true
  default     = null
}

variable "redis_ssl_enabled" {
  description = "Whether SSL is enabled for Redis connection"
  type        = bool
  default     = false
}

variable "enable_redis_sidecar" {
  description = "Whether to run a Redis 7 container inside the Container App pod (Zero cost, no retirement issues)"
  type        = bool
  default     = true
}

variable "redis_sidecar_image" {
  description = "Docker image for Redis sidecar"
  type        = string
  default     = "redis:7-alpine"
}

variable "ddl_auto" {
  description = "Hibernate ddl-auto setting (update, validate, none)"
  type        = string
  default     = "update"
}

variable "extra_env_vars" {
  description = "Additional environment variables map"
  type        = map(string)
  default     = {}
}

variable "registry_server" {
  description = "Docker registry server (e.g. myacr.azurecr.io). Set if pulling from private registry"
  type        = string
  default     = null
}

variable "registry_username" {
  description = "Docker registry username"
  type        = string
  default     = null
}

variable "registry_password" {
  description = "Docker registry password"
  type        = string
  sensitive   = true
  default     = null
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
  default     = {}
}
