package mobi.sevenwinds.app.budget

import mobi.sevenwinds.app.author.AuthorEntity
import mobi.sevenwinds.app.author.AuthorTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object BudgetTable : IntIdTable("budget") {
    val year = integer("year")
    val month = integer("month")
    val amount = integer("amount")
    val type = enumerationByName("type", 100, BudgetType::class)
    val author = optReference("author_id", AuthorTable.id)
}

class BudgetEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BudgetEntity>(BudgetTable)

    var year by BudgetTable.year
    var month by BudgetTable.month
    var amount by BudgetTable.amount
    var type by BudgetTable.type
    var author by BudgetTable.author

    fun toResponse(): BudgetRecord {
        val recordDate = author?.let {
            transaction {
                val query = AuthorTable
                    .select { AuthorTable.id eq author?.value }
                return@transaction AuthorEntity.wrapRow(query.first()).creationDate
            }
        }

        val authorFullName = author?.let {
            transaction {
                val query = AuthorTable
                    .select { AuthorTable.id eq author?.value }
                return@transaction AuthorEntity.wrapRow(query.first()).fullName
            }
        }
        return BudgetRecord(year, month, amount, type, author?.value, authorFullName, recordDate)
    }
}