package pdfreader.infuturetech.com.jiopdfviewerlite.manipulation.progressive.downloads.document;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;

import com.glidebitmappool.GlideBitmapPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pdfreader.infuturetech.com.jiopdfviewerlite.manipulation.utils.PDFUtil;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.io.MemoryUsageSetting;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.multipdf.PDFMergerUtility;
import pdfreader.infuturetech.com.jiopdfviewerlite.pdflite.pdfbox.pdmodel.PDDocument;

public class AdaptiveDocImpl implements AdaptiveDoc
{

    @Override
    public void addPage( String combineId,String pagePath )
    {
        File page = new File(pagePath);
        try
        {
            File originalFile = new File(PDFUtil.getFilePath(String.valueOf(PDFUtil.getItemId(combineId))));

            if(!originalFile.exists())
            {
                originalFile.createNewFile();
                PDDocument document = new PDDocument();
                document.save(originalFile);
                document.close();
            }
            PDFMergerUtility utility = new PDFMergerUtility();
            utility.addSource(page);
            utility.addSource(originalFile);
            utility.setDestinationStream(new FileOutputStream(originalFile));
            utility.mergeDocuments(MemoryUsageSetting.setupMixed(10*1024*1024));
            page.delete();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    /**
     * Check if the single page pdf is present in the disk
     * or else check if it is present in merged pdf
     * @param combineId
     * @return
     */
    @Override
    public Bitmap getPage( String combineId )
    {
        try
        {
            ParcelFileDescriptor descriptor = getDescriptor(new File(PDFUtil.getFilePath(combineId)));

            if(descriptor == null)
            {
                descriptor = getDescriptor(new File(PDFUtil.getFilePath(combineId)));
            }
            if(descriptor == null)
            {
                return null;
            }
            PdfRenderer renderer = new PdfRenderer(descriptor);
            PdfRenderer.Page page = renderer.openPage(0);
            Bitmap bm = GlideBitmapPool.getBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bm, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            page.close();
            return bm;
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private ParcelFileDescriptor getDescriptor(File file) throws IOException
    {
        return file.exists()
                ? ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY)
                : null;
    }
}
