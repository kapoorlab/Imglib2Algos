package varun;

import java.io.File;

import net.imglib2.Cursor;
import net.imglib2.FinalInterval;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import util.ImgLib2Util;

public class HoughTransform {

	public static <T extends RealType<T>> void Houghspace(Img<T> inputimage, double mintheta,
			int maxtheta) {

		
		//mintheta = 0;
		//maxtheta = 180;
		
		
		final long[] position = new long[inputimage.numDimensions()];
		double deltatheta = Math.PI / maxtheta;
		

		double[] theta = new double[maxtheta];
		double[] rho = new double[maxtheta];

		for (int angle = 0; angle < maxtheta; ++angle) {
			theta[angle] = deltatheta * angle + mintheta;
		}
		

		final Cursor<T> inputcursor = inputimage.localizingCursor();
		
		
		while (inputcursor.hasNext()) {
			

			inputcursor.fwd();
			inputcursor.localize(position);

			for (int angle = 0; angle < maxtheta; ++angle) {

				rho[angle] = Math.cos(theta[angle]) * position[0] + Math.sin(theta[angle]) * position[1];

			}

		}

	}

	public static void main(String[] args) {
		final Img<FloatType> inputimg = ImgLib2Util.openAs32Bit(new File("src/main/resources/dt.png"));

		final ImgFactory<FloatType> imgFactory = new CellImgFactory<FloatType>(5);
		int maxtheta = 180;
		int maxrho = (int) (2*Math.sqrt((inputimg.dimension(0) * inputimg.dimension(0) 
				+ inputimg.dimension(1) * inputimg.dimension(1))));
		FinalInterval interval = new FinalInterval(new long[] { maxrho, maxtheta });

		final Img<FloatType> houghimage = imgFactory.create(interval, new FloatType());

	}

}
