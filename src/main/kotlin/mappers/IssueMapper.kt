package mappers

import dto.IssueDTO
import model.Issue
import repositories.ProgrammerRepository
import repositories.ProjectRepository
import repositories.RepositoryRepository
import utils.Utils

/**
 * Clase encargada de mapear un objeto Issue pasandolo a DTO o a la inversa.
 * @author Daniel Rodríguez
 * @see BaseMapper
 */
class IssueMapper : BaseMapper<Issue, IssueDTO>() {

    /**
     * Coge un IssueDTO y lo convierte en un Issue
     * @author Daniel Rodríguez
     * @param item IssueDTO
     * @return Issue
     */
    override fun fromDTO(item: IssueDTO): Issue {
        Utils().makeSureTheseAreIds(item.id, item.author.id,
                item.project.id, item.repository.id)
        Utils().makeSureThisGuyIsProjectManager(item.author, item.project.id)
        return Issue(
                item.id, item.author, item.title, item.text,
                Utils().matchesDate(item.date), item.programmers,
                item.project, item.repository, item.isFinished
        )
    }

    /**
     * Coge un Issue y lo convierte en IssueDTO
     * @author Daniel Rodríguez
     * @param item Issue
     * @return IssueDTO
     */
    override fun toDTO(item: Issue): IssueDTO {
        Utils().makeSureTheseAreIds(item.id, item.author.id,
                item.project.id, item.repository.id)

        Utils().makeSureThisGuyIsProjectManager(item.author, item.project.id)
        return IssueDTO(
                item.id, item.author, item.title, item.text,
                Utils().matchesDate(item.date), item.programmers,
                item.project, item.repository, item.isFinished
        )
    }
}