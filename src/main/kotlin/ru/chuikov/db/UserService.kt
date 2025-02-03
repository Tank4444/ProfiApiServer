package ru.chuikov.db

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.SerialName


@Serializable
data class User(
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("password")
    val password: String,
    @SerialName("patronymic")
    val patronymic: String
)

interface UserRepository {
    fun allTasks(): List<User>
    fun UserByName(name: String): User?
    fun addUser(User: User)
    fun removeUser(name: String): Boolean
}


class UserService(database: Database) {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val first_name = varchar("first_name", length = 50)
        val last_name = varchar("last_name", length = 50)
        val patronymic = varchar("patronymic", length = 50)
        val email = varchar("email", length = 50)
        val password = varchar("password", length = 50)
        val birth_date = varchar("birth_date", length = 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(user: User): Int = dbQuery {
        Users.insert {
            it[first_name] = user.firstName
            it[last_name] = user.lastName
            it[patronymic] = user.patronymic
            it[email] = user.email
            it[password] = user.password
            it[birth_date] = user.birthDate
        }[Users.id]
    }

    suspend fun read(id: Int): User? {
        return dbQuery {
            Users.selectAll()
                .where { Users.id eq id }
                .map {
                    User(
                        firstName = it[Users.first_name],
                        lastName =  it[Users.last_name],
                        patronymic = it[Users.patronymic],
                        email = it[Users.email],
                        password = it[Users.password],
                        birthDate = it[Users.birth_date]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun update(id: Int, user: User) {
//        dbQuery {
//            Users.update({ Users.id eq id }) {
//                it[name] = user.name
//                it[age] = user.age
//            }
//        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }
}