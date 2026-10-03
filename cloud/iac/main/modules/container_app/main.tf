resource "azurerm_container_app_environment" "this" {
  name                       = "cae-${var.app_name}"
  location                   = var.location
  resource_group_name        = var.resource_group_name
  log_analytics_workspace_id = var.log_analytics_workspace_id
  tags                       = var.tags
}

resource "azurerm_container_app" "this" {
  name                         = "ca-${var.app_name}"
  container_app_environment_id = azurerm_container_app_environment.this.id
  resource_group_name          = var.resource_group_name
  revision_mode                = "Single"
  tags                         = var.tags

  secret {
    name  = "db-password"
    value = var.datasource_password
  }

  dynamic "secret" {
    for_each = var.redis_password != null ? [1] : []
    content {
      name  = "redis-password"
      value = var.redis_password
    }
  }

  dynamic "secret" {
    for_each = var.registry_password != null ? [1] : []
    content {
      name  = "registry-password"
      value = var.registry_password
    }
  }

  dynamic "registry" {
    for_each = var.registry_server != null ? [1] : []
    content {
      server               = var.registry_server
      username             = var.registry_username
      password_secret_name = "registry-password"
    }
  }

  ingress {
    external_enabled = true
    target_port      = var.target_port
    transport        = "auto"

    traffic_weight {
      percentage      = 100
      latest_revision = true
    }
  }

  template {
    min_replicas = var.min_replicas
    max_replicas = var.max_replicas

    container {
      name   = "catalog-service"
      image  = var.container_image
      cpu    = var.cpu
      memory = var.memory

      # Base application configurations
      env {
        name  = "SERVER_PORT"
        value = tostring(var.target_port)
      }

      env {
        name  = "SPRING_APPLICATION_NAME"
        value = "catalog-service"
      }

      env {
        name  = "SPRING_DATASOURCE_URL"
        value = var.datasource_url
      }

      env {
        name  = "SPRING_DATASOURCE_USERNAME"
        value = var.datasource_username
      }

      env {
        name        = "SPRING_DATASOURCE_PASSWORD"
        secret_name = "db-password"
      }

      env {
        name  = "SPRING_JPA_HIBERNATE_DDL_AUTO"
        value = var.ddl_auto
      }

      env {
        name  = "SPRING_DATA_REDIS_HOST"
        value = var.enable_redis_sidecar ? "localhost" : var.redis_host
      }

      env {
        name  = "SPRING_DATA_REDIS_PORT"
        value = var.enable_redis_sidecar ? "6379" : tostring(var.redis_port)
      }

      dynamic "env" {
        for_each = !var.enable_redis_sidecar && var.redis_password != null ? [1] : []
        content {
          name        = "SPRING_DATA_REDIS_PASSWORD"
          secret_name = "redis-password"
        }
      }

      env {
        name  = "SPRING_DATA_REDIS_SSL_ENABLED"
        value = var.enable_redis_sidecar ? "false" : tostring(var.redis_ssl_enabled)
      }

      env {
        name  = "SPRING_CACHE_TYPE"
        value = var.cache_type
      }

      env {
        name  = "MANAGEMENT_HEALTH_REDIS_ENABLED"
        value = "false"
      }

      env {
        name  = "MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS"
        value = "always"
      }

      env {
        name  = "SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT"
        value = "org.hibernate.dialect.PostgreSQLDialect"
      }

      dynamic "env" {
        for_each = var.cache_type != "redis" ? [1] : []
        content {
          name  = "SPRING_AUTOCONFIGURE_EXCLUDE"
          value = "org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration,org.springframework.boot.data.redis.autoconfigure.DataRedisReactiveAutoConfiguration,org.springframework.boot.data.redis.autoconfigure.DataRedisRepositoriesAutoConfiguration,org.springframework.boot.data.redis.autoconfigure.health.DataRedisHealthContributorAutoConfiguration,org.springframework.boot.data.redis.autoconfigure.health.DataRedisReactiveHealthContributorAutoConfiguration,org.springframework.boot.data.redis.autoconfigure.observation.LettuceObservationAutoConfiguration"
        }
      }

      env {
        name  = "SPRING_DATA_REDIS_LETTUCE_POOL_MIN_IDLE"
        value = "0"
      }

      env {
        name  = "SPRING_DATA_REDIS_LETTUCE_CLUSTER_REFRESH_ADAPTIVE"
        value = "false"
      }

      dynamic "env" {
        for_each = var.extra_env_vars
        content {
          name  = env.key
          value = env.value
        }
      }

      liveness_probe {
        transport               = "HTTP"
        port                    = var.target_port
        path                    = "/catalog-svc/actuator/health/liveness"
        initial_delay           = 30
        interval_seconds        = 15
        timeout                 = 5
        failure_count_threshold = 5
      }

      readiness_probe {
        transport               = "HTTP"
        port                    = var.target_port
        path                    = "/catalog-svc/actuator/health/readiness"
        initial_delay           = 25
        interval_seconds        = 10
        timeout                 = 5
        failure_count_threshold = 5
        success_count_threshold = 1
      }
    }

    dynamic "container" {
      for_each = var.enable_redis_sidecar ? [1] : []
      content {
        name   = "redis"
        image  = var.redis_sidecar_image
        cpu    = 0.25
        memory = "0.5Gi"
      }
    }
  }
}
