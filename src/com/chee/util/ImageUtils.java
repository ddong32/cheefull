/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import com.jfinal.log.Log;
/*   4:    */ import java.awt.AlphaComposite;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Graphics2D;
/*   7:    */ import java.awt.Image;
/*   8:    */ import java.awt.Rectangle;
/*   9:    */ import java.awt.image.BufferedImage;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.FileInputStream;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import javax.imageio.ImageIO;
/*  16:    */ import javax.imageio.ImageReadParam;
/*  17:    */ import javax.imageio.ImageReader;
/*  18:    */ import javax.imageio.stream.ImageInputStream;
/*  19:    */ 
/*  20:    */ public class ImageUtils
/*  21:    */ {
/*  22: 23 */   private static final Log log = Log.getLog(ImageUtils.class);
/*  23:    */   
/*  24:    */   public static int[] ratio(String src)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 26 */     BufferedImage bufferedImage = ImageIO.read(new File(src));
/*  28: 27 */     int width = bufferedImage.getWidth();
/*  29: 28 */     int height = bufferedImage.getHeight();
/*  30: 29 */     return new int[] { width, height };
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static String ratioAsString(String src)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 33 */     File file = new File(src);
/*  37: 34 */     if (!file.exists()) {
/*  38: 35 */       return null;
/*  39:    */     }
/*  40: 37 */     BufferedImage bufferedImage = ImageIO.read(file);
/*  41: 38 */     int width = bufferedImage.getWidth();
/*  42: 39 */     int height = bufferedImage.getHeight();
/*  43: 40 */     return String.format("%s x %s", new Object[] { Integer.valueOf(width), Integer.valueOf(height) });
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static String scale(String src, int w, int h)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 44 */     int inserTo = src.lastIndexOf(".");
/*  50: 45 */     String dest = src.substring(0, inserTo) + String.format("_%sx%s", new Object[] { Integer.valueOf(w), Integer.valueOf(h) }) + src.substring(inserTo, src.length());
/*  51: 46 */     scale(src, dest, w, h);
/*  52: 47 */     return dest;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static void scale(String src, String dest, int w, int h)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 60 */     String srcSuffix = src.substring(src.lastIndexOf(".") + 1);
/*  59: 61 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(srcSuffix);
/*  60: 62 */     ImageReader reader = (ImageReader)iterator.next();
/*  61:    */     
/*  62: 64 */     InputStream in = new FileInputStream(src);
/*  63: 65 */     ImageInputStream iis = ImageIO.createImageInputStream(in);
/*  64: 66 */     reader.setInput(iis);
/*  65:    */     
/*  66: 68 */     BufferedImage srcBuffered = readBuffereImage(reader, w, h);
/*  67: 69 */     BufferedImage targetBuffered = new BufferedImage(w, h, 1);
/*  68:    */     
/*  69: 71 */     Graphics graphics = targetBuffered.getGraphics();
/*  70: 72 */     graphics.drawImage(srcBuffered.getScaledInstance(w, h, 1), 0, 0, null);
/*  71:    */     
/*  72: 74 */     graphics.dispose();
/*  73: 75 */     srcBuffered.flush();
/*  74:    */     
/*  75: 77 */     ImageIO.write(targetBuffered, srcSuffix, new File(dest));
/*  76: 78 */     targetBuffered.flush();
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static BufferedImage readBuffereImage(ImageReader reader, int w, int h)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82: 82 */     ImageReadParam param = reader.getDefaultReadParam();
/*  83: 83 */     int srcWidth = reader.getWidth(0);
/*  84: 84 */     int srcHeight = reader.getHeight(0);
/*  85:    */     
/*  86: 86 */     Rectangle rect = null;
/*  87: 88 */     if (w / h > srcWidth / srcHeight)
/*  88:    */     {
/*  89: 89 */       h = h * srcWidth / w;
/*  90: 90 */       w = srcWidth;
/*  91: 91 */       rect = new Rectangle(0, (srcHeight - h) / 2, w, h);
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95: 93 */       w = w * srcHeight / h;
/*  96: 94 */       h = srcHeight;
/*  97: 95 */       rect = new Rectangle((srcWidth - w) / 2, 0, w, h);
/*  98:    */     }
/*  99: 97 */     param.setSourceRegion(rect);
/* 100: 98 */     BufferedImage srcBuffered = reader.read(0, param);
/* 101: 99 */     return srcBuffered;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static final void pressImage(String watermarkImg, String srcImageFile)
/* 105:    */   {
/* 106:103 */     pressImage(watermarkImg, srcImageFile, srcImageFile, 5, -1, -1, 0.2F, 1.0F);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static final void pressImage(String watermarkImg, String srcImageFile, String destImageFile)
/* 110:    */   {
/* 111:107 */     pressImage(watermarkImg, srcImageFile, destImageFile, 5, -1, -1, 0.2F, 1.0F);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static final void pressImage(String watermarkImg, String srcImageFile, String destImageFile, int position, float alpha)
/* 115:    */   {
/* 116:112 */     pressImage(watermarkImg, srcImageFile, destImageFile, position, -1, -1, 0.2F, alpha);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static final void pressImage(String watermarkImg, String srcImageFile, String destImageFile, int position, int xOffset, int yOffset, float radio, float alpha)
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:136 */       File img = new File(srcImageFile);
/* 124:137 */       Image src = ImageIO.read(img);
/* 125:138 */       int srcWidth = src.getWidth(null);
/* 126:139 */       int srcHeight = src.getHeight(null);
/* 127:    */       
/* 128:141 */       BufferedImage image = new BufferedImage(srcWidth, srcHeight, 1);
/* 129:142 */       Graphics2D graphics = image.createGraphics();
/* 130:143 */       graphics.drawImage(src, 0, 0, srcWidth, srcHeight, null);
/* 131:    */       
/* 132:    */ 
/* 133:146 */       Image wmImage = ImageIO.read(new File(watermarkImg));
/* 134:147 */       int wmWidth = wmImage.getWidth(null);
/* 135:148 */       int wmHeight = wmImage.getHeight(null);
/* 136:    */       
/* 137:150 */       radio = radio <= 0.0F ? 0.2F : radio;
/* 138:151 */       int newWidth = (int)(srcWidth * radio);
/* 139:152 */       int newHeight = (int)(wmHeight * (newWidth / wmWidth));
/* 140:    */       
/* 141:154 */       xOffset = xOffset < 0 ? (int)(newWidth * 0.1F) : xOffset;
/* 142:155 */       yOffset = yOffset < 0 ? (int)(newHeight * 0.1F) : yOffset;
/* 143:    */       
/* 144:157 */       int xPostion = 0;
/* 145:158 */       int yPostion = 0;
/* 146:160 */       switch (position)
/* 147:    */       {
/* 148:    */       case 1: 
/* 149:162 */         xPostion = xOffset;
/* 150:163 */         yPostion = yOffset;
/* 151:164 */         break;
/* 152:    */       case 2: 
/* 153:166 */         xPostion = (int)(srcWidth * (1.0F - radio) - xOffset);
/* 154:167 */         yPostion = yOffset;
/* 155:168 */         break;
/* 156:    */       case 3: 
/* 157:170 */         xPostion = (srcWidth - newWidth) / 2;
/* 158:171 */         yPostion = (srcHeight - newHeight) / 2;
/* 159:172 */         break;
/* 160:    */       case 4: 
/* 161:174 */         xPostion = xOffset;
/* 162:175 */         yPostion = srcHeight - newHeight - yOffset;
/* 163:176 */         break;
/* 164:    */       case 5: 
/* 165:178 */         xPostion = (int)(srcWidth * (1.0F - radio) - xOffset);
/* 166:179 */         yPostion = srcHeight - newHeight - yOffset;
/* 167:180 */         break;
/* 168:    */       default: 
/* 169:182 */         xPostion = xOffset;
/* 170:183 */         yPostion = yOffset;
/* 171:    */       }
/* 172:187 */       graphics.setComposite(AlphaComposite.getInstance(10, alpha));
/* 173:188 */       graphics.drawImage(wmImage, xPostion, yPostion, newWidth, newHeight, null);
/* 174:    */       
/* 175:190 */       graphics.dispose();
/* 176:191 */       ImageIO.write(image, "JPEG", new File(destImageFile));
/* 177:    */     }
/* 178:    */     catch (Exception e)
/* 179:    */     {
/* 180:193 */       log.warn("ImageUtils pressImage error", e);
/* 181:    */     }
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.ImageUtils
 * JD-Core Version:    0.7.0.1
 */