package com.gustavo.gastoprevisto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gustavo.gastoprevisto.web.rest.TestUtil;

public class TiposFinanceiroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiposFinanceiro.class);
        TiposFinanceiro tiposFinanceiro1 = new TiposFinanceiro();
        tiposFinanceiro1.setId(1L);
        TiposFinanceiro tiposFinanceiro2 = new TiposFinanceiro();
        tiposFinanceiro2.setId(tiposFinanceiro1.getId());
        assertThat(tiposFinanceiro1).isEqualTo(tiposFinanceiro2);
        tiposFinanceiro2.setId(2L);
        assertThat(tiposFinanceiro1).isNotEqualTo(tiposFinanceiro2);
        tiposFinanceiro1.setId(null);
        assertThat(tiposFinanceiro1).isNotEqualTo(tiposFinanceiro2);
    }
}
