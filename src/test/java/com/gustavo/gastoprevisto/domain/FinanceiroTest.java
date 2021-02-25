package com.gustavo.gastoprevisto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gustavo.gastoprevisto.web.rest.TestUtil;

public class FinanceiroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Financeiro.class);
        Financeiro financeiro1 = new Financeiro();
        financeiro1.setId(1L);
        Financeiro financeiro2 = new Financeiro();
        financeiro2.setId(financeiro1.getId());
        assertThat(financeiro1).isEqualTo(financeiro2);
        financeiro2.setId(2L);
        assertThat(financeiro1).isNotEqualTo(financeiro2);
        financeiro1.setId(null);
        assertThat(financeiro1).isNotEqualTo(financeiro2);
    }
}
