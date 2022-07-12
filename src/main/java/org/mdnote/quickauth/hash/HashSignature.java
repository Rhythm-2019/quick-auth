package org.mdnote.quickauth.hash;

import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description
 */
public interface HashSignature {
    String hash(Map<String, String> param);
}
