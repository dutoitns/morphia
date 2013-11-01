/**
 * Copyright (C) 2010 Olafur Gauti Gudmundsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.mongodb.morphia;


import com.mongodb.DBCollection;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.mapping.MappedClass;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author Scott Hernandez
 */
public class TestEmbeddedArrayIndexes extends TestBase {
    @Indexes({@Index("b.bar, b.car")})
    private static class A {
        @Id
        private ObjectId id = new ObjectId();
        private Set<B> b;
        @Indexed
        private String foo;
    }

    private static class B {
        private String bar;
        private String car;
    }

    @Test
    public void testParamEntity() throws Exception {
        final MappedClass mc = getMorphia().getMapper().getMappedClass(A.class);
        assertNotNull(mc);

        assertEquals(1, mc.getAnnotations(Indexes.class).size());

        getDs().ensureIndexes(A.class);
        final DBCollection coll = getDs().getCollection(A.class);

        assertEquals("indexes found: coll.getIndexInfo()" + coll.getIndexInfo(), 3, coll.getIndexInfo().size());

    }

}
