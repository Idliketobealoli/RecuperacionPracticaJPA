package repositories

import hibernate.HController
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
class DepartmentRepository : IRepository<Department, String> {
    /**
     * Encuentra todos los repositories presentes en la BD y los devuelve como una lista de objetos department
     * @author Daniel Rodríguez
     * @return List<Department>
     */
    override fun findAll(): List<Department> {
        try {
            val query = HController.manager
                .createNamedQuery(
                    "Department.findAll",
                    Department::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error at DepartmentRepository.findAll")
        }
    }

    /**
     * Encuentra el department cuyo ID casa con el parámetro introducido y lo devuelve como un objeto Department, si lo encuentra.
     * @author Daniel Rodríguez
     * @param id String
     * @return Department
     */
    override fun getById(id: String): Department {
        return HController.manager.find(Department::class.java, id) ?:
        throw SQLException("Error at DepartmentRepository.getById: Department with id:$id does not exist.")
    }

    /**
     * Inserta un department en la base de datos, donde cada atributo del department va a un campo de la tabla department,
     * devolviendo dicho department si lo consigue.
     * @author Daniel Rodríguez
     * @param department Department
     * @return Department
     */
    override fun insert(department: Department): Department {
        try {
            HController.transaction.begin()
            HController.manager.persist(department)
            HController.transaction.commit()
            return department
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at DepartmentRepository.insert: Could not insert Department into BD: ${e.message}")
        }
    }

    /**
     * Modifica un department, si existe, devolviendo dicho department si lo consigue.
     * @author Daniel Rodríguez
     * @param department Department
     * @return Department
     */
    override fun update(department: Department): Department {
        try {
            HController.transaction.begin()
            HController.manager.merge(department)
            HController.transaction.commit()
            return department
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at DepartmentRepository.update: Could not update Department: ${e.message}")
        }
    }

    /**
     * Borra un department, si existe, devolviendo dicho department si lo consigue.
     * @author Daniel Rodríguez
     * @param department Department
     * @return Department
     */
    override fun delete(department: Department): Department {
        try {
            val itemDelete = HController.manager.find(Department::class.java, department.id)
            HController.transaction.begin()
            HController.manager.remove(itemDelete)
            HController.transaction.commit()
            return itemDelete
        } catch (e: Exception) {
            HController.transaction.rollback()
            throw SQLException("Error at DepartmentRepository.delete: Could not delete Department with id: ${department.id}: ${e.message}")
        }
    }
}