package com.example.deepart.core.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class RenderScriptBlur {
    public static Bitmap blur(Context context, Bitmap targetBitmap, int radius) throws RSRuntimeException {
        RenderScript renderScript = null;
        try {
            renderScript = RenderScript.create(context);
            Allocation input = Allocation.createFromBitmap(renderScript, targetBitmap,
                    Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(renderScript, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

            blur.setInput(input);
            blur.setRadius((radius > 25 ? 25 : radius));
            blur.forEach(output);
            output.copyTo(targetBitmap);

        } finally {
            if (renderScript != null) {
                renderScript.destroy();
            }
        }
        return targetBitmap;
    }
}

