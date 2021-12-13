package repositories

import hibernate.HController
import model.Repository
import java.sql.SQLException

/**
 * Clase encargada de hacer las operaciones CRUD de Repository.
 * @author Jaime Salcedo
 * @see IRepository
 */
class RepositoryRepository : IRepository<Repository, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos Repository
     * @author Jaime Salcedo
     * @return List<Repository>
     */
    override fun findAll(): List<Repository> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Repository.findAll",
                    Repository::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at RepositoryRepository.findAll")
        }
    }

    /**
     * Encuentra el repository cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Repository, si lo encuentra.
     * @author Jaime Salcedo
     * @param id String
     * @return Repository
     */
    override fun getById(id: String): Repository {
        return HController.manager.find(Repository::class.java, id) ?:
        throw SQLException("Error at RepositoryRepository.getById: Repository with id:$id does not exist.")
    }

    /**
     * Inserta un repository en la base de datos, donde cada atributo del repository va a un campo de la tabla repository,
     * devolviendo dicho repository si lo consigue.
     * @author Jaime Salcedo
     * @param repository Repository
     * @return Repository
     */
    override fun insert(repository: Repository): Repository {
        try {
            HController.transaction.begin()
            HController.manager.persist(repository)
            HController.transaction.commit()
            return repository
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at RepositoryRepository.insert: Could not insert Repository into BD: ${e.message}")
        }
    }

    /**
     * Modifica un repository, si existe, devolviendo dicho repository si lo consigue.
     * @author Jaime Salcedo
     * @param repository Repository
     * @return Repository
     */
    override fun update(repository: Repository): Repository {
        try {
            HController.transaction.begin()
            HController.manager.merge(repository)
            HController.transaction.commit()
            return repository
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at RepositoryRepository.update: Could not update Repository: ${e.message}")
        }
    }

    /**
     * Borra un repository, si existe, devolviendo dicho repository si lo consigue.
     * @author Jaime Salcedo
     * @param repository Repository
     * @return Repository
     */
    override fun delete(repository: Repository): Repository {
        try {
            val itemDelete = HController.manager.find(Repository::class.java, repository.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at RepositoryRepository.delete: Could not delete Repository with id: ${repository.id}: ${e.message}")
        }
    }
}