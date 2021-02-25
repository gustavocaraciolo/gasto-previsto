package com.gustavo.gastoprevisto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gustavo.gastoprevisto.web.rest.TestUtil;

public class TipoFinanceiroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoFinanceiro.class);
        TipoFinanceiro tipoFinanceiro1 = new TipoFinanceiro();
        tipoFinanceiro1.setId(1L);
        TipoFinanceiro tipoFinanceiro2 = new TipoFinanceiro();
        tipoFinanceiro2.setId(tipoFinanceiro1.getId());
        assertThat(tipoFinanceiro1).isEqualTo(tipoFinanceiro2);
        tipoFinanceiro2.setId(2L);
        assertThat(tipoFinanceiro1).isNotEqualTo(tipoFinanceiro2);
        tipoFinanceiro1.setId(null);
        assertThat(tipoFinanceiro1).isNotEqualTo(tipoFinanceiro2);
    }
}
