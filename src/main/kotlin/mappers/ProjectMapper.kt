package mappers

import dto.ProjectDTO
import model.Project
import repositories.DepartmentRepository
import repositories.ProgrammerRepository
import repositories.RepositoryRepository
import utils.Utils

/**
 * Clase encargada de mapear un objeto Project pasandolo a DTO o a la inversa.
 * @author Jaime Salcedo
 * @see BaseMapper
 */
class ProjectMapper : BaseMapper<Project, ProjectDTO>() {

    /**
     * Coge un ProjectDTO y lo convierte en un Project
     * @author Jaime Salcedo
     * @param item ProjectDTO
     * @return Project
     */
    override fun fromDTO(item: ProjectDTO): Project {
        Utils().makeSureTheseAreIds(item.id, item.department.id,
                item.projectManager.id, item.repository.id)
        Utils().makeSureThisGuyIsProjectManager(item.projectManager, item.id)
        return Project(
                item.id, item.department, item.projectManager,
                item.name, item.budget,
                Utils().matchesDate(item.startDate),
                Utils().matchesDateAcceptingNull(item.endDate),
                item.technologies, item.repository, item.isFinished,
                item.programmers
        )
    }

    /**
     * Coge un Project y lo convierte en ProjectDTO
     * @author Jaime Salcedo
     * @param item Project
     * @return ProjectDTO
     */
    override fun toDTO(item: Project): ProjectDTO {
        Utils().makeSureTheseAreIds(item.id, item.department.id,
                item.projectManager.id, item.repository.id)
        Utils().makeSureThisGuyIsProjectManager(item.projectManager, item.id)
        return ProjectDTO(
                item.id, item.department, item.projectManager,
                item.name, item.budget, Utils().matchesDate(item.startDate),
                Utils().matchesDateAcceptingNull(item.endDate),
                item.technologies, item.repository, item.isFinished,
                item.programmers
        )
    }
}