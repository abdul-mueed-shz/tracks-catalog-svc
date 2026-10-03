locals {
  name_prefix = "${var.project_name}-${var.environment}"
}

# 1. Resource Group & Log Analytics Workspace
module "resource_group" {
  source = "./modules/resource_group"

  resource_group_name = "rg-${local.name_prefix}"
  location            = var.location
  tags                = var.tags
}

# 2. Managed PostgreSQL Flexible Server
module "postgresql" {
  source = "./modules/postgresql"

  server_name_prefix     = "psql-${local.name_prefix}"
  resource_group_name    = module.resource_group.resource_group_name
  location               = module.resource_group.resource_group_location
  postgres_version       = var.postgres_version
  administrator_login    = var.postgres_admin_login
  administrator_password = var.postgres_admin_password
  sku_name               = var.postgres_sku
  storage_mb             = var.postgres_storage_mb
  database_name          = var.postgres_database_name
  allowed_ip_addresses   = var.allowed_ip_addresses
  tags                   = var.tags
}

# 3. Azure Cache for Redis (Optional managed service; by default runs as zero-cost container in Container App)
module "redis" {
  count  = var.use_managed_redis ? 1 : 0
  source = "./modules/redis"

  redis_name_prefix      = "redis-${local.name_prefix}"
  resource_group_name    = module.resource_group.resource_group_name
  location               = module.resource_group.resource_group_location
  sku_name               = var.redis_sku
  family                 = var.redis_family
  capacity               = var.redis_capacity
  allow_all_ips_for_demo = var.allow_all_redis_ips_for_demo
  tags                   = var.tags
}

# 4. Azure Container Registry (Optional)
module "acr" {
  count  = var.enable_acr ? 1 : 0
  source = "./modules/acr"

  acr_name_prefix     = replace(replace("acr${local.name_prefix}", "-", ""), "_", "")
  resource_group_name = module.resource_group.resource_group_name
  location            = module.resource_group.resource_group_location
  tags                = var.tags
}

# 5. Azure Container App Environment & Container App (catalog-service)
module "container_app" {
  source = "./modules/container_app"

  app_name                   = local.name_prefix
  resource_group_name        = module.resource_group.resource_group_name
  location                   = module.resource_group.resource_group_location
  log_analytics_workspace_id = module.resource_group.log_analytics_workspace_id

  container_image = (
    var.enable_acr ?
    "${module.acr[0].login_server}/catalog-service:latest" :
    var.container_image
  )

  cpu          = var.container_cpu
  memory       = var.container_memory
  min_replicas = var.min_replicas
  max_replicas = var.max_replicas

  # Database Connection
  datasource_url      = module.postgresql.jdbc_url
  datasource_username = module.postgresql.administrator_login
  datasource_password = module.postgresql.administrator_password

  # Redis Connection (Defaults to zero-cost Redis 7 container inside Container App)
  enable_redis_sidecar = !var.use_managed_redis
  redis_host           = var.use_managed_redis ? module.redis[0].hostname : "localhost"
  redis_port           = var.use_managed_redis ? module.redis[0].ssl_port : 6379
  redis_password       = var.use_managed_redis ? module.redis[0].primary_access_key : null
  redis_ssl_enabled    = var.use_managed_redis

  # Registry Credentials (if ACR is enabled)
  registry_server   = var.enable_acr ? module.acr[0].login_server : null
  registry_username = var.enable_acr ? module.acr[0].admin_username : null
  registry_password = var.enable_acr ? module.acr[0].admin_password : null

  extra_env_vars = var.extra_env_vars
  tags           = var.tags
}
