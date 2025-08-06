# 📦 API de Pedidos

Bem-vindo à API de Pedidos!  
Esta é uma API RESTful desenvolvida em **Kotlin + Spring Boot**, seguindo os princípios de **Clean Architecture**, **DDD** e **Hexagonal Architecture**, com persistência em arquivo `.json`.

Ela permite cadastrar, listar e cancelar pedidos com controle de estoque e validações de negócio.

---

## ✅ Requisitos

Para rodar o projeto localmente, você precisa ter instalado:

- Java Development Kit (JDK) 17+
- Apache Maven
- Spring Boot
- Ferramenta de testes REST (ex: Insomnia, Postman)

---

## 🚀 Como executar

1. Clone o repositório:

```bash
git clone https://github.com/edmundontf/api-pedidos.git
```

2. Acesse o diretório do projeto:

```bash
cd api-pedidos
```

3. Execute o projeto com Maven:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

---

## 🧪 Como testar a API

### ✅ 1. Criar um novo pedido

**Requisição:**  
Método: `POST`  
URL: `/api/pedidos`

**Body:**
```json
{
  "itens": [
    {
      "produtoId": "00000000-0000-0000-0000-000000000001",
      "descricao": "Teclado Mecânico",
      "quantidade": 2,
      "precoUnitario": 200.00
    },
    {
      "produtoId": "00000000-0000-0000-0000-000000000002",
      "descricao": "Mouse Gamer",
      "quantidade": 1,
      "precoUnitario": 150.00
    }
  ]
}
```

**Resposta esperada:**  
Status: `201 Created`
```json
{
  "id": "b843e768-9b3c-4a84-bdc4-54228db728af"
}
```

---

### ✅ 2. Cancelar um pedido

**Requisição:**  
Método: `DELETE`  
URL: `/api/pedidos/{id}`

**Exemplo:**
```http
DELETE http://localhost:8080/api/pedidos/b843e768-9b3c-4a84-bdc4-54228db728af
```

**Resposta esperada:**  
Status: `200 OK`  
O pedido é marcado como `CANCELADO` no arquivo `pedidos.json`.

---

### ✅ 3. Listar pedidos ativos

**Requisição:**  
Método: `GET`  
URL: `/api/pedidos`

**Resposta esperada:**
```json
[
  {
    "id": "b843e768-9b3c-4a84-bdc4-54228db728af",
    "dataCriacao": "2025-08-04T21:34:00.123",
    "valorTotal": 550.0,
    "status": "ATIVO",
    "itens": [
      {
        "produtoId": "00000000-0000-0000-0000-000000000001",
        "descricao": "Teclado Mecânico",
        "quantidade": 2,
        "precoUnitario": 200.0
      }
    ]
  }
]
```

---

## ❌ Testes de Erro

| Cenário                     | Simulação                                    | Esperado                  |
|----------------------------|----------------------------------------------|---------------------------|
| Produto inexistente        | Use `produtoId` inválido                     | `400 Bad Request`         |
| Estoque insuficiente       | Use `quantidade: 100` com pouco estoque      | `400 Bad Request`         |
| Pedido sem itens           | Envie `{ "itens": [] }`                      | `400 Bad Request`         |
| Pedido já cancelado        | Envie `DELETE` duas vezes no mesmo ID       | `400 Bad Request`         |
| ID de pedido inválido      | Use UUID inexistente                         | `404 Not Found`           |

---

## 🧪 Testes com Insomnia/Postman

| Situação                   | Ação                                          |
|---------------------------|-----------------------------------------------|
| Criar pedido válido       | POST `/api/pedidos` com JSON válido           |
| Produto inexistente       | Alterar `produtoId` para UUID aleatório       |
| Estoque insuficiente      | Usar `quantidade > 10`                        |
| Pedido sem itens          | Enviar `{ "itens": [] }`                      |
| Cancelar pedido           | DELETE com ID válido                          |
| Cancelar duas vezes       | DELETE duas vezes seguidas                    |
| Listar pedidos ativos     | GET `/api/pedidos`                            |
| Ver histórico completo    | Visualizar manualmente `pedidos.json`         |

---

## 🚀 Melhorias Recomendadas

| Recurso                   | O que implementar                             |
|--------------------------|-----------------------------------------------|
| Retornar ID no POST      | Usar `ResponseEntity.created().body(id)`      |
| GET `/api/pedidos/{id}`  | Criar endpoint para buscar pedido específico  |
| Swagger/OpenAPI          | Adicionar `springdoc-openapi-ui`              |
| Validações customizadas  | Mensagens de erro mais descritivas            |

---

## 📁 Estrutura de Persistência

- `pedidos.json`: todos os pedidos (ativos e cancelados)
- `produtos.json`: estoque e dados dos produtos

---
