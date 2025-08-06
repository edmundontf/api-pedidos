package domain.model

data class Pedido(
    val id: String,
    val descricao: String,
    val quantidade: Int,
    val precoUnitario: Double,
    val ativo: Boolean = true
)

// Requisitos atendidos:
// Pedido deve ter descrição, quantidade e preço unitário.
// Identifica se o pedido está ativo ou cancelado. ( ativo true e cancelado false )