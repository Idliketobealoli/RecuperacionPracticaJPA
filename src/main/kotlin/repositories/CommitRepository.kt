package repositories

import hibernate.HController
import model.Commit
import model.Department
import model.Issue
import java.sql.ResultSet
import java.sql.SQLException
import java.util.ArrayList

/**
 * Clase encargada de hacer las operaciones CRUD de Repository.
 * @author Daniel Rodríguez
 * @see IRepository
 */
class CommitRepository : IRepository<Commit, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos commit
     * @author Daniel Rodríguez
     * @return List<Commit>
     */
    override fun findAll(): List<Commit> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Commite.findAll",
                    Commit::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at CommitRepository.findAll")
        }
    }

    /**
     * Encuentra el commit cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Commit, si lo encuentra.
     * @author Daniel Rodríguez
     * @param id String
     * @return Commit
     */
    override fun getById(id: String): Commit {
        return HController.manager.find(Commit::class.java, id) ?:
        throw SQLException("Error at CommitRepository.getById: Commit with id:$id does not exist.")
    }

    /**
     * Inserta un commit en la base de datos, donde cada atributo del commit va a un campo de la tabla commit,
     * devolviendo dicho commit si lo consigue.
     * @author Daniel Rodríguez
     * @param commit Commit
     * @return Commit
     */
    override fun insert(commit: Commit): Commit {
        try {
            HController.transaction.begin()
            HController.manager.persist(commit)
            HController.transaction.commit()
            return commit
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at CommitRepository.insert: Could not insert Commit into BD: ${e.message}")
        }
    }

    /**
     * Modifica un commit, si existe, devolviendo dicho commit si lo consigue.
     * @author Daniel Rodríguez
     * @param commit Commit
     * @return Commit
     */
    override fun update(commit: Commit): Commit {
        try {
            HController.transaction.begin()
            HController.manager.merge(commit)
            HController.transaction.commit()
            return commit
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at CommitRepository.update: Could not update Commit: ${e.message}")
        }
    }

    /**
     * Borra un commit, si existe, devolviendo dicho commit si lo consigue.
     * @author Daniel Rodríguez
     * @param commit Commit
     * @return Commit
     */
    override fun delete(commit: Commit): Commit {
        try {
            val itemDelete = HController.manager.find(Commit::class.java, commit.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at CommitRepository.delete: Could not delete Commit with id: ${commit.id}: ${e.message}")
        }
    }
}