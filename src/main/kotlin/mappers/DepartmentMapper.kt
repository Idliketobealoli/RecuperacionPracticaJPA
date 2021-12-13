package mappers

import dto.DepartmentDTO
import model.Department
import model.Project
import repositories.ProgrammerRepository
import utils.Utils
import java.util.ArrayList

/**
 * Clase encargada de mapear un objeto Department pasandolo a DTO o a la inversa.
 * @author Daniel Rodríguez
 * @see BaseMapper
 */
class DepartmentMapper : BaseMapper<Department, DepartmentDTO>() {

    /**
     * Coge un DepartmentDTO y lo convierte en un Department
     * @author Daniel Rodríguez
     * @param item DepartmentDTO
     * @return Department
     */
    override fun fromDTO(item: DepartmentDTO): Department {
        Utils().makeSureTheseAreIds(item.id, item.boss.id)
        Utils().makeSureThisGuyIsDepBoss(item.boss);
        var condition = false
        item.bossHistory.forEach { x ->
            run {
                if (x.id.contentEquals(item.boss.id)) {
                    condition = true;
                }
            }
        }
        if (item.bossHistory.isEmpty() || !condition) {
            item.bossHistory.plus(item.boss)
        }
        var projectsIDS = ""
        val str1 = Utils().getProjectsIDS(item.finishedProjects)
        val str2 = Utils().getProjectsIDS(item.developingProjects)
        if (str1.isNotEmpty()) projectsIDS += str1
        if (str2.isNotEmpty()) projectsIDS += str2
        val projects = Utils().getProjects(projectsIDS)
        val finishedProjects = projects?.filter { project: Project -> project.isFinished }
        val developingProjects = projects?.filter { project: Project -> !project.isFinished }
        var anualBudget = item.budget
        developingProjects?.forEach { anualBudget += it.budget }

        return Department(
                item.id, item.name, item.boss, item.budget,
                finishedProjects, developingProjects,
                anualBudget, item.bossHistory
        )
    }

    /**
     * Coge un Department y lo convierte en DepartmenttDTO
     * @author Daniel Rodríguez
     * @param item Department
     * @return DepartmentDTO
     */
    override fun toDTO(item: Department): DepartmentDTO {
        Utils().makeSureTheseAreIds(item.id, item.boss.id)
        val boss = ProgrammerRepository().getById(item.boss.id)
        Utils().makeSureThisGuyIsDepBoss(boss)
        if (item.bossHistory.isNullOrEmpty()) item.bossHistory = listOf(boss)
        if (!(item.bossHistory.contains(boss))) item.bossHistory.plus(boss)
        val projects = ArrayList<Project>()
        item.finishedProjects?.forEach { x ->
            run {
                projects.plus(x)
            }
        }
        item.developingProjects?.forEach { x ->
            run {
                projects.plus(x)
            }
        }
        val finishedProjects = projects.filter { project: Project -> project.isFinished }
        val developingProjects = projects.filter { project: Project -> !project.isFinished }
        var anualBudget = item.budget
        developingProjects.forEach { anualBudget += it.budget }
        return DepartmentDTO(
                item.id, item.name, boss, item.budget,
                finishedProjects, developingProjects,
                anualBudget, item.bossHistory
        )
    }
}