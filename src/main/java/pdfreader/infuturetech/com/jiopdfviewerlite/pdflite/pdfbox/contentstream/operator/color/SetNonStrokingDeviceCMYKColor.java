/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.contentstream.operator.color;

import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.contentstream.operator.Operator;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.cos.COSBase;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.cos.COSName;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.pdmodel.graphics.color.PDColorSpace;

import java.io.IOException;
import java.util.List;

/**
 * k: Set the non-stroking colour space to DeviceCMYK and set the colour to
 * use for non-stroking operations.
 *
 * @author John Hewson
 */
public class SetNonStrokingDeviceCMYKColor extends SetNonStrokingColor
{
    @Override
    public void process(Operator operator, List<COSBase> arguments) throws IOException
    {
        PDColorSpace cs = context.getResources().getColorSpace(COSName.DEVICECMYK);
        context.getGraphicsState().setNonStrokingColorSpace(cs);
        super.process(operator, arguments);
    }

    @Override
    public String getName()
    {
        return "k";
    }
}
