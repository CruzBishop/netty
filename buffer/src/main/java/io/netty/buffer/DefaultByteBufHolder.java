/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.buffer;

/**
 * Default implementation of a {@link ByteBufHolder} that holds it's data in a {@link ByteBuf}.
 *
 */
public class DefaultByteBufHolder implements ByteBufHolder {
    private final ByteBuf data;
    public DefaultByteBufHolder(ByteBuf data) {
        if (data == null) {
            throw new NullPointerException("data");
        }
        if (data.unwrap() != null && !(data instanceof SwappedByteBuf)) {
            throw new IllegalArgumentException("Only not-derived ByteBuf instance are supported, you used: "
                    + data.getClass().getSimpleName());
        }
        this.data = data;
    }

    @Override
    public ByteBuf data() {
        if (data.isFreed()) {
            throw new IllegalBufferAccessException();
        }
        return data;
    }

    @Override
    public void free() {
        data.free();
    }

    @Override
    public boolean isFreed() {
        return data.isFreed();
    }

    @Override
    public ByteBufHolder copy() {
        return new DefaultByteBufHolder(data().copy());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '(' + data().toString() + ')';
    }
}
