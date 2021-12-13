package repositories

import hibernate.HController
import model.Project
import model.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.util.ArrayList

/**
 * Clase encargada de hacer las operaciones CRUD de Repository.
 * @author Jaime Salcedo
 * @see IRepository
 */
class ProjectRepository : IRepository<Project, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos Project
     * @author Jaime Salcedo
     * @return List<Project>
     */
    override fun findAll(): List<Project> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Project.findAll",
                    Project::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at ProjectRepository.findAll")
        }
    }

    /**
     * Encuentra el project cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Project, si lo encuentra.
     * @author Jaime Salcedo
     * @param id String
     * @return Project
     */
    override fun getById(id: String): Project {
        return HController.manager.find(Project::class.java, id) ?:
        throw SQLException("Error at ProjectRepository.getById: Project with id:$id does not exist.")
    }

    /**
     * Inserta un project en la base de datos, donde cada atributo del project va a un campo de la tabla project,
     * devolviendo dicho project si lo consigue.
     * @author Jaime Salcedo
     * @param project Project
     * @return Project
     */
    override fun insert(project: Project): Project {
        try {
            HController.transaction.begin()
            HController.manager.persist(project)
            HController.transaction.commit()
            return project
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProjectRepository.insert: Could not insert Project into BD: ${e.message}")
        }
    }

    /**
     * Modifica un project, si existe, devolviendo dicho project si lo consigue.
     * @author Jaime Salcedo
     * @param project Project
     * @return Project
     */
    override fun update(project: Project): Project {
        try {
            HController.transaction.begin()
            HController.manager.merge(project)
            HController.transaction.commit()
            return project
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProjectRepository.update: Could not update Project: ${e.message}")
        }
    }

    /**
     * Borra un project, si existe, devolviendo dicho project si lo consigue.
     * @author Jaime Salcedo
     * @param project Project
     * @return Project
     */
    override fun delete(project: Project): Project {
        try {
            val itemDelete = HController.manager.find(Project::class.java, project.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProjectRepository.delete: Could not delete Project with id: ${project.id}: ${e.message}")
        }
    }
}