package repositories

import hibernate.HController
import model.Issue
import model.Programmer
import java.sql.ResultSet
import java.sql.SQLException
import java.util.ArrayList

/**
 * Clase encargada de hacer las operaciones CRUD de Repository.
 * @author Daniel Rodriguez
 * @see IRepository
 */
class IssueRepository : IRepository<Issue, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos Issue
     * @author Daniel Rodriguez
     * @return List<Issue>
     */
    override fun findAll(): List<Issue> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Issue.findAll",
                    Issue::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at IssueRepository.findAll")
        }
    }

    /**
     * Encuentra el issue cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Issue, si lo encuentra.
     * @author Daniel Rodriguez
     * @param id String
     * @return Issue
     */
    override fun getById(id: String): Issue {
        return HController.manager.find(Issue::class.java, id) ?:
        throw SQLException("Error at IssueRepository.getById: Issue with id:$id does not exist.")
    }

    /**
     * Inserta un issue en la base de datos, donde cada atributo del issue va a un campo de la tabla issue,
     * devolviendo dicho issue si lo consigue.
     * @author Daniel Rodriguez
     * @param issue Issue
     * @return Issue
     */
    override fun insert(issue: Issue): Issue {
        try {
            HController.transaction.begin()
            HController.manager.persist(issue)
            HController.transaction.commit()
            return issue
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at IssueRepository.insert: Could not insert Issue into BD: ${e.message}")
        }
    }

    /**
     * Modifica un issue, si existe, devolviendo dicho issue si lo consigue.
     * @author Daniel Rodriguez
     * @param issue Issue
     * @return Issue
     */
    override fun update(issue: Issue): Issue {
        try {
            HController.transaction.begin()
            HController.manager.merge(issue)
            HController.transaction.commit()
            return issue
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at IssueRepository.update: Could not update Issue: ${e.message}")
        }
    }

    /**
     * Borra un issue, si existe, devolviendo dicho issue si lo consigue.
     * @author Daniel Rodriguez
     * @param issue Issue
     * @return Issue
     */
    override fun delete(issue: Issue): Issue {
        try {
            val itemDelete = HController.manager.find(Issue::class.java, issue.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at IssueRepository.delete: Could not delete Issue with id: ${issue.id}: ${e.message}")
        }
    }
}