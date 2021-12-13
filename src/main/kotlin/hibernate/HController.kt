package hibernate

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

object HController {
    private val eManagerFact = Persistence.createEntityManagerFactory("default")
    val manager: EntityManager = eManagerFact.createEntityManager()
    val transaction : EntityTransaction = manager.transaction
}