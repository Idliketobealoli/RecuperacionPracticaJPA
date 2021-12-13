package mappers

import dto.ProgrammerDTO
import model.Programmer
import repositories.DepartmentRepository
import utils.Utils

/**
 * Clase encargada de mapear un objeto Programmer pasandolo a DTO o a la inversa.
 * @author Jaime Salcedo
 * @see BaseMapper
 */
class ProgrammerMapper : BaseMapper<Programmer, ProgrammerDTO>() {

    /**
     * Coge un ProgrammerDTO y lo convierte en un Programmer
     * @author Jaime Salcedo
     * @param item ProgrammerDTO
     * @return Programmer
     */
    override fun fromDTO(item: ProgrammerDTO): Programmer {
        Utils().makeSureBooleansAreCorrect(item)
        Utils().makeSureTheseAreIds(item.id, item.department.id)
        return Programmer(
                item.id, item.name, Utils().matchesDate(item.registerDate),
                item.department, item.activeProjects, item.commits,
                item.issues, item.technologies, item.salary,
                item.isDepBoss, item.isProjectManager, item.isActive,
                item.password
        )
    }

    /**
     * Coge un Programmer y lo convierte en ProgrammerDTO
     * @author Jaime Salcedo
     * @param item Programmer
     * @return ProgrammerDTO
     */
    override fun toDTO(item: Programmer): ProgrammerDTO {
        Utils().makeSureBooleansAreCorrect(item)
        Utils().makeSureTheseAreIds(item.id, item.department.id)
        return ProgrammerDTO(
                item.id, item.name, Utils().matchesDate(item.registerDate),
                item.department, item.activeProjects, item.commits,
                item.issues, item.technologies, item.salary, item.isDepBoss,
                item.isProjectManager, item.isActive, item.password
        )
    }
}