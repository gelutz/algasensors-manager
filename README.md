# AlgaSensors Manager

Device and sensor management API for AlgaSensors. Provides endpoints for managing sensors, configuring alerts, and retrieving monitoring data.

## Running

### Development

1. Load environment variables:
   ```bash
   set -a; source .env.dev; set +a
   ```
   (Works in bash/zsh)

2. Start the development server:
   ```bash
   ./gradlew bootRun
   ```

The application will be available at `http://localhost:8080`.

### Docker Compose

Start via docker-compose from the root directory:

```bash
docker-compose up algasensors-manager -d
```

## Environment

Key environment variables (see `.env.dev` for all options):

- `SPRING_APPLICATION_NAME=manager` - Service name
- `SERVER_PORT=8080` - HTTP port
- `SPRING_DATASOURCE_URL` - H2 database connection
- `SPRING_DATASOURCE_USERNAME` - Database user
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_RABBITMQ_HOST` - RabbitMQ host
- `SPRING_RABBITMQ_PORT` - RabbitMQ port
- `SPRING_RABBITMQ_USERNAME` - RabbitMQ credentials
- `SPRING_RABBITMQ_PASSWORD` - RabbitMQ credentials
- `APP_SERVICES_MONITORING_URL` - Monitor service URL

## Related Services

- [Monitor Service](../monitor/README.md) - Temperature monitoring
- [Processor Service](../processor/README.md) - Data processing
- [Client](../client/README.md) - Web dashboard
