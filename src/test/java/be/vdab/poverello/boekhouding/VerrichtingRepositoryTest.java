package be.vdab.poverello.boekhouding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VerrichtingRepositoryTest {
    private static final String VERRICHTINGEN_TABLE = "kleinekassa";
    private final VerrichtingRepository verrichtingRepository;
    private final JdbcClient jdbcClient;

    public VerrichtingRepositoryTest(VerrichtingRepository verrichtingRepository, JdbcClient jdbcClient) {
        this.verrichtingRepository = verrichtingRepository;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void countVindtHetJuisteAantalVerrichtingen() {
        assertThat(verrichtingRepository.count())
                .isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, VERRICHTINGEN_TABLE));
    }
}