package com.gustavo.gastoprevisto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gustavo.gastoprevisto.web.rest.TestUtil;

public class PropriedadesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propriedades.class);
        Propriedades propriedades1 = new Propriedades();
        propriedades1.setId(1L);
        Propriedades propriedades2 = new Propriedades();
        propriedades2.setId(propriedades1.getId());
        assertThat(propriedades1).isEqualTo(propriedades2);
        propriedades2.setId(2L);
        assertThat(propriedades1).isNotEqualTo(propriedades2);
        propriedades1.setId(null);
        assertThat(propriedades1).isNotEqualTo(propriedades2);
    }
}
