# SQL QueryDSL MCP Server 

This project is a **schema-based QueryDSL MCP Server (STDIO)** that accepts JSON query objects from an AI client and returns filtered records from a SQL database.  
The repository includes an example implementation using the **Employees** model as a reference.

---

## 🚀 Requirements

- **Java SDK**  
- **Maven**  
- **A SQL Database** (PostgreSQL recommended)

---

## 📘 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/divaxi/SQL-QueryDSL-query-mcp-server.git
cd <REPO_FOLDER>
```
### 2. Adjust the code to match your database
Modify the schema, configuration, and Spring Boot properties, etc... to fit your own SQL database.
### 3. Build the project
```bash
mvn clean package -DskipTests
```
### 4. Run or test with MCP Inspector

You can validate your MCP server using [**MCP Inspector**]([https://modelcontextprotocol.io/docs/tools/inspector](https://modelcontextprotocol.io/docs/tools/inspector))
## 💬 Using with ChatGPT / Claude Desktop / VS Code
add this following JSON.
```json
{
  "mcpServers": {
    "server-name": {
      "command": "java",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "/ABSOLUTE/PATH/TO/PARENT/FOLDER/ai-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}

```
