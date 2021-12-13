package repositories

import hibernate.HController
import model.Programmer
import model.Project
import java.sql.ResultSet
import java.sql.SQLException
import java.util.ArrayList

/**
 * Clase encargada de hacer las operaciones CRUD de Repository.
 * @author Jaime Salcedo
 * @see IRepository
 */
class ProgrammerRepository : IRepository<Programmer, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos Programmer
     * @author Jaime Salcedo
     * @return List<Programmer>
     */
    override fun findAll(): List<Programmer> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Programmer.findAll",
                    Programmer::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at ProgrammerRepository.findAll")
        }
    }

    /**
     * Encuentra el programmer cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Programmer, si lo encuentra.
     * @author Jaime Salcedo
     * @param id String
     * @return Programmer
     */
    override fun getById(id: String): Programmer {
        return HController.manager.find(Programmer::class.java, id) ?:
        throw SQLException("Error at ProgrammerRepository.getById: Programmer with id:$id does not exist.")
    }

    /**
     * Inserta un programmer en la base de datos, donde cada atributo del programmer va a un campo de la tabla programmer,
     * devolviendo dicho programmer si lo consigue.
     * @author Jaime Salcedo
     * @param programmer Programmer
     * @return Programmer
     */
    override fun insert(programmer: Programmer): Programmer {
        try {
            HController.transaction.begin()
            HController.manager.persist(programmer)
            HController.transaction.commit()
            return programmer
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProrammerRepository.insert: Could not insert Programmer into BD: ${e.message}")
        }
    }

    /**
     * Modifica un programmer, si existe, devolviendo dicho programmer si lo consigue.
     * @author Jaime Salcedo
     * @param programmer Programmer
     * @return Programmer
     */
    override fun update(programmer: Programmer): Programmer {
        try {
            HController.transaction.begin()
            HController.manager.merge(programmer)
            HController.transaction.commit()
            return programmer
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProgrammerRepository.update: Could not update Programmer: ${e.message}")
        }
    }

    /**
     * Borra un programmer, si existe, devolviendo dicho programmer si lo consigue.
     * @author Jaime Salcedo
     * @param programmer Programmer
     * @return Programmer
     */
    override fun delete(programmer: Programmer): Programmer {
        try {
            val itemDelete = HController.manager.find(Programmer::class.java, programmer.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at ProgrammerRepository.delete: Could not delete Programmer with id: ${programmer.id}: ${e.message}")
        }
    }
}