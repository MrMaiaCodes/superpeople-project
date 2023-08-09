package br.com.mrmaia.superpeope.storage.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_BATTLE")
@SequenceGenerator(name = "SEQ_BATTLE")

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BATTLE")
    @Column(name = "ID_BATTLE")
    private Long id;

    @Column(name = "DS_COMBATANTONE")
    @JoinColumn(name = "DS_SUPERPEOPLE")
    private SuperPeople superHeroOne;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "DS_SUPERPEOPLE")
    private List<SuperPeople> opponentList;

    @Column(name = "DS_COMBATANTTWO")
    @JoinColumn(name = "DS_SUPERPEOPLE")
    private SuperPeople superHeroTwo;

    @Column(name = "DS_WINNER")
    @JoinColumn(name = "DS_SUPERPEOPLE")
    private SuperPeople winner;

    @Column(name = "DS_LOSER")
    @JoinColumn(name = "DS_SUPERPEOPLE")
    private SuperPeople loser;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

}
