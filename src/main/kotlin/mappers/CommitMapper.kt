package mappers

import dto.CommitDTO
import model.Commit
import repositories.IssueRepository
import repositories.ProgrammerRepository
import repositories.ProjectRepository
import repositories.RepositoryRepository
import utils.Utils

/**
 * Clase encargada de mapear un objeto Commit pasandolo a DTO o a la inversa.
 * @author Daniel Rodríguez
 * @see BaseMapper
 */
class CommitMapper : BaseMapper<Commit, CommitDTO>() {

    /**
     * Coge un commitDTO y lo convierte en un Commit
     * @author Daniel Rodríguez
     * @param item CommitDTO
     * @return Commit
     */
    override fun fromDTO(item: CommitDTO): Commit {
        Utils().makeSureTheseAreIds(item.id, item.repository.id,
                item.project.id, item.author.id, item.issue.id)
        Utils().makeSureThisProgrammerIsInThisIssue(item.author.id, item.issue.id)
        return Commit(
                item.id, item.title, item.text,
                Utils().matchesDate(item.date),
                item.repository, item.project,
                item.author, item.issue
        )
    }

    /**
     * Coge un commit y lo convierte en CommitDTO
     * @author Daniel Rodríguez
     * @param item Commit
     * @return CommitDTO
     */
    override fun toDTO(item: Commit): CommitDTO {
        Utils().makeSureTheseAreIds(item.id, item.repository.id,
                item.project.id, item.author.id, item.issue.id)
        Utils().makeSureThisProgrammerIsInThisIssue(item.author.id, item.issue.id)
        return CommitDTO(
                item.id, item.title, item.text, Utils().matchesDate(item.date),
                item.repository, item.project, item.author, item.issue
        )
    }
}