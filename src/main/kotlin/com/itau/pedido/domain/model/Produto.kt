package domain.model

data class Produto(
    val id: String,
    val nome: String,
    val estoque: Int,
    val precoUnitario: Double
)

// Requisitos atendidos:
// Controla produtos válidos.
// Controla estoque.