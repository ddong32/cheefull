/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.OrderBy;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import org.hibernate.annotations.Fetch;
/*  21:    */ import org.hibernate.annotations.FetchMode;
/*  22:    */ import org.hibernate.annotations.GenericGenerator;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="chwx_content")
/*  26:    */ public class WxContent
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   private Integer id;
/*  31:    */   private String title;
/*  32:    */   private String text;
/*  33:    */   private String summary;
/*  34:    */   private String link_to;
/*  35:    */   private Integer markdown_enable;
/*  36:    */   private String thumbnail;
/*  37:    */   private String module;
/*  38:    */   private String style;
/*  39:    */   private User user;
/*  40:    */   private String author;
/*  41:    */   private String user_email;
/*  42:    */   private String user_ip;
/*  43:    */   private String user_agent;
/*  44:    */   private Integer object_id;
/*  45:    */   private Integer order_number;
/*  46:    */   private String status;
/*  47:    */   private Integer vote_up;
/*  48:    */   private Integer vote_down;
/*  49:    */   private Integer rate;
/*  50:    */   private Integer rate_count;
/*  51:    */   private double price;
/*  52:    */   private String comment_status;
/*  53:    */   private Integer comment_count;
/*  54:    */   private Date comment_time;
/*  55:    */   private Integer view_count;
/*  56:    */   private Date created;
/*  57:    */   private Date modified;
/*  58:    */   private String slug;
/*  59:    */   private String flag;
/*  60:    */   private Integer lng;
/*  61:    */   private Integer lat;
/*  62:    */   private String meta_keywords;
/*  63:    */   private String meta_description;
/*  64:    */   private String remarks;
/*  65:    */   private String ucode;
/*  66:    */   private String beginDate;
/*  67:    */   private String endDate;
/*  68:    */   private WxContent parent;
/*  69:    */   private Set<WxContent> children;
/*  70:    */   private List<WxContent> childList;
/*  71:    */   
/*  72:    */   @Id
/*  73:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  74:    */   @GeneratedValue(generator="paymentableGenerator", strategy=GenerationType.AUTO)
/*  75:    */   @Column(name="ID", precision=11, scale=0)
/*  76:    */   public Integer getId()
/*  77:    */   {
/*  78: 79 */     return this.id;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setId(Integer id)
/*  82:    */   {
/*  83: 83 */     this.id = id;
/*  84:    */   }
/*  85:    */   
/*  86:    */   @Column(name="title ")
/*  87:    */   public String getTitle()
/*  88:    */   {
/*  89: 88 */     return this.title;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTitle(String title)
/*  93:    */   {
/*  94: 92 */     this.title = title;
/*  95:    */   }
/*  96:    */   
/*  97:    */   @Column(name="text")
/*  98:    */   public String getText()
/*  99:    */   {
/* 100: 97 */     return this.text;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setText(String text)
/* 104:    */   {
/* 105:101 */     this.text = text;
/* 106:    */   }
/* 107:    */   
/* 108:    */   @Column(name="summary")
/* 109:    */   public String getSummary()
/* 110:    */   {
/* 111:106 */     return this.summary;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setSummary(String summary)
/* 115:    */   {
/* 116:110 */     this.summary = summary;
/* 117:    */   }
/* 118:    */   
/* 119:    */   @Column(name="link_to")
/* 120:    */   public String getLink_to()
/* 121:    */   {
/* 122:115 */     return this.link_to;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setLink_to(String link_to)
/* 126:    */   {
/* 127:119 */     this.link_to = link_to;
/* 128:    */   }
/* 129:    */   
/* 130:    */   @Column(name="markdown_enable")
/* 131:    */   public Integer getMarkdown_enable()
/* 132:    */   {
/* 133:124 */     return this.markdown_enable;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setMarkdown_enable(Integer markdown_enable)
/* 137:    */   {
/* 138:128 */     this.markdown_enable = markdown_enable;
/* 139:    */   }
/* 140:    */   
/* 141:    */   @Column(name="thumbnail")
/* 142:    */   public String getThumbnail()
/* 143:    */   {
/* 144:133 */     return this.thumbnail;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setThumbnail(String thumbnail)
/* 148:    */   {
/* 149:137 */     this.thumbnail = thumbnail;
/* 150:    */   }
/* 151:    */   
/* 152:    */   @Column(name="module")
/* 153:    */   public String getModule()
/* 154:    */   {
/* 155:142 */     return this.module;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setModule(String module)
/* 159:    */   {
/* 160:146 */     this.module = module;
/* 161:    */   }
/* 162:    */   
/* 163:    */   @Column(name="style")
/* 164:    */   public String getStyle()
/* 165:    */   {
/* 166:151 */     return this.style;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setStyle(String style)
/* 170:    */   {
/* 171:155 */     this.style = style;
/* 172:    */   }
/* 173:    */   
/* 174:    */   @ManyToOne(fetch=FetchType.LAZY, optional=true)
/* 175:    */   @JoinColumn(name="USER_ID", referencedColumnName="ID")
/* 176:    */   public User getUser()
/* 177:    */   {
/* 178:161 */     return this.user;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setUser(User user)
/* 182:    */   {
/* 183:165 */     this.user = user;
/* 184:    */   }
/* 185:    */   
/* 186:    */   @Column(name="author")
/* 187:    */   public String getAuthor()
/* 188:    */   {
/* 189:170 */     return this.author;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setAuthor(String author)
/* 193:    */   {
/* 194:174 */     this.author = author;
/* 195:    */   }
/* 196:    */   
/* 197:    */   @Column(name="user_email")
/* 198:    */   public String getUser_email()
/* 199:    */   {
/* 200:179 */     return this.user_email;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setUser_email(String user_email)
/* 204:    */   {
/* 205:183 */     this.user_email = user_email;
/* 206:    */   }
/* 207:    */   
/* 208:    */   @Column(name="user_ip")
/* 209:    */   public String getUser_ip()
/* 210:    */   {
/* 211:188 */     return this.user_ip;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setUser_ip(String user_ip)
/* 215:    */   {
/* 216:192 */     this.user_ip = user_ip;
/* 217:    */   }
/* 218:    */   
/* 219:    */   @Column(name="user_agent")
/* 220:    */   public String getUser_agent()
/* 221:    */   {
/* 222:197 */     return this.user_agent;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setUser_agent(String user_agent)
/* 226:    */   {
/* 227:201 */     this.user_agent = user_agent;
/* 228:    */   }
/* 229:    */   
/* 230:    */   @Column(name="object_id")
/* 231:    */   public Integer getObject_id()
/* 232:    */   {
/* 233:206 */     return this.object_id;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setObject_id(Integer object_id)
/* 237:    */   {
/* 238:210 */     this.object_id = object_id;
/* 239:    */   }
/* 240:    */   
/* 241:    */   @Column(name="order_number")
/* 242:    */   public Integer getOrder_number()
/* 243:    */   {
/* 244:215 */     return this.order_number;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setOrder_number(Integer order_number)
/* 248:    */   {
/* 249:219 */     this.order_number = order_number;
/* 250:    */   }
/* 251:    */   
/* 252:    */   @Column(name="status")
/* 253:    */   public String getStatus()
/* 254:    */   {
/* 255:224 */     return this.status;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setStatus(String status)
/* 259:    */   {
/* 260:228 */     this.status = status;
/* 261:    */   }
/* 262:    */   
/* 263:    */   @Column(name="vote_up")
/* 264:    */   public Integer getVote_up()
/* 265:    */   {
/* 266:233 */     return this.vote_up;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setVote_up(Integer vote_up)
/* 270:    */   {
/* 271:237 */     this.vote_up = vote_up;
/* 272:    */   }
/* 273:    */   
/* 274:    */   @Column(name="vote_down")
/* 275:    */   public Integer getVote_down()
/* 276:    */   {
/* 277:242 */     return this.vote_down;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setVote_down(Integer vote_down)
/* 281:    */   {
/* 282:246 */     this.vote_down = vote_down;
/* 283:    */   }
/* 284:    */   
/* 285:    */   @Column(name="rate")
/* 286:    */   public Integer getRate()
/* 287:    */   {
/* 288:251 */     return this.rate;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setRate(Integer rate)
/* 292:    */   {
/* 293:255 */     this.rate = rate;
/* 294:    */   }
/* 295:    */   
/* 296:    */   @Column(name="rate_count")
/* 297:    */   public Integer getRate_count()
/* 298:    */   {
/* 299:260 */     return this.rate_count;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setRate_count(Integer rate_count)
/* 303:    */   {
/* 304:264 */     this.rate_count = rate_count;
/* 305:    */   }
/* 306:    */   
/* 307:    */   @Column(name="price")
/* 308:    */   public double getPrice()
/* 309:    */   {
/* 310:269 */     return this.price;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setPrice(double price)
/* 314:    */   {
/* 315:273 */     this.price = price;
/* 316:    */   }
/* 317:    */   
/* 318:    */   @Column(name="comment_status")
/* 319:    */   public String getComment_status()
/* 320:    */   {
/* 321:278 */     return this.comment_status;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setComment_status(String comment_status)
/* 325:    */   {
/* 326:282 */     this.comment_status = comment_status;
/* 327:    */   }
/* 328:    */   
/* 329:    */   @Column(name="comment_count")
/* 330:    */   public Integer getComment_count()
/* 331:    */   {
/* 332:287 */     return this.comment_count;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setComment_count(Integer comment_count)
/* 336:    */   {
/* 337:291 */     this.comment_count = comment_count;
/* 338:    */   }
/* 339:    */   
/* 340:    */   @Column(name="comment_time")
/* 341:    */   public Date getComment_time()
/* 342:    */   {
/* 343:296 */     return this.comment_time;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setComment_time(Date comment_time)
/* 347:    */   {
/* 348:300 */     this.comment_time = comment_time;
/* 349:    */   }
/* 350:    */   
/* 351:    */   @Column(name="view_count")
/* 352:    */   public Integer getView_count()
/* 353:    */   {
/* 354:305 */     return this.view_count;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setView_count(Integer view_count)
/* 358:    */   {
/* 359:309 */     this.view_count = view_count;
/* 360:    */   }
/* 361:    */   
/* 362:    */   @Column(name="created")
/* 363:    */   public Date getCreated()
/* 364:    */   {
/* 365:314 */     return this.created;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setCreated(Date created)
/* 369:    */   {
/* 370:318 */     this.created = created;
/* 371:    */   }
/* 372:    */   
/* 373:    */   @Column(name="modified")
/* 374:    */   public Date getModified()
/* 375:    */   {
/* 376:323 */     return this.modified;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setModified(Date modified)
/* 380:    */   {
/* 381:327 */     this.modified = modified;
/* 382:    */   }
/* 383:    */   
/* 384:    */   @Column(name="slug")
/* 385:    */   public String getSlug()
/* 386:    */   {
/* 387:332 */     return this.slug;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setSlug(String slug)
/* 391:    */   {
/* 392:336 */     this.slug = slug;
/* 393:    */   }
/* 394:    */   
/* 395:    */   @Column(name="flag")
/* 396:    */   public String getFlag()
/* 397:    */   {
/* 398:341 */     return this.flag;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setFlag(String flag)
/* 402:    */   {
/* 403:345 */     this.flag = flag;
/* 404:    */   }
/* 405:    */   
/* 406:    */   @Column(name="lng")
/* 407:    */   public Integer getLng()
/* 408:    */   {
/* 409:350 */     return this.lng;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setLng(Integer lng)
/* 413:    */   {
/* 414:354 */     this.lng = lng;
/* 415:    */   }
/* 416:    */   
/* 417:    */   @Column(name="lat")
/* 418:    */   public Integer getLat()
/* 419:    */   {
/* 420:359 */     return this.lat;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setLat(Integer lat)
/* 424:    */   {
/* 425:363 */     this.lat = lat;
/* 426:    */   }
/* 427:    */   
/* 428:    */   @Column(name="meta_keywords")
/* 429:    */   public String getMeta_keywords()
/* 430:    */   {
/* 431:368 */     return this.meta_keywords;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setMeta_keywords(String meta_keywords)
/* 435:    */   {
/* 436:372 */     this.meta_keywords = meta_keywords;
/* 437:    */   }
/* 438:    */   
/* 439:    */   @Column(name="meta_description")
/* 440:    */   public String getMeta_description()
/* 441:    */   {
/* 442:377 */     return this.meta_description;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setMeta_description(String meta_description)
/* 446:    */   {
/* 447:381 */     this.meta_description = meta_description;
/* 448:    */   }
/* 449:    */   
/* 450:    */   @Column(name="remarks")
/* 451:    */   public String getRemarks()
/* 452:    */   {
/* 453:386 */     return this.remarks;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setRemarks(String remarks)
/* 457:    */   {
/* 458:390 */     this.remarks = remarks;
/* 459:    */   }
/* 460:    */   
/* 461:    */   @Transient
/* 462:    */   public String getUcode()
/* 463:    */   {
/* 464:396 */     return this.ucode;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void setUcode(String ucode)
/* 468:    */   {
/* 469:400 */     this.ucode = ucode;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public int hashCode()
/* 473:    */   {
/* 474:404 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 475:    */   }
/* 476:    */   
/* 477:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 478:    */   @JoinColumn(name="parent_id", referencedColumnName="id")
/* 479:    */   public WxContent getParent()
/* 480:    */   {
/* 481:410 */     return this.parent;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setParent(WxContent parent)
/* 485:    */   {
/* 486:414 */     this.parent = parent;
/* 487:    */   }
/* 488:    */   
/* 489:    */   @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
/* 490:    */   @Fetch(FetchMode.SUBSELECT)
/* 491:    */   @OrderBy("order_number")
/* 492:    */   public Set<WxContent> getChildren()
/* 493:    */   {
/* 494:421 */     return this.children;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setChildren(Set<WxContent> children)
/* 498:    */   {
/* 499:425 */     this.children = children;
/* 500:    */   }
/* 501:    */   
/* 502:    */   @Transient
/* 503:    */   public String getBeginDate()
/* 504:    */   {
/* 505:430 */     return this.beginDate;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void setBeginDate(String beginDate)
/* 509:    */   {
/* 510:434 */     this.beginDate = beginDate;
/* 511:    */   }
/* 512:    */   
/* 513:    */   @Transient
/* 514:    */   public String getEndDate()
/* 515:    */   {
/* 516:439 */     return this.endDate;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void setEndDate(String endDate)
/* 520:    */   {
/* 521:443 */     this.endDate = endDate;
/* 522:    */   }
/* 523:    */   
/* 524:    */   @Transient
/* 525:    */   public void addChild(WxContent child)
/* 526:    */   {
/* 527:448 */     if (this.childList == null) {
/* 528:449 */       this.childList = new ArrayList();
/* 529:    */     }
/* 530:453 */     if (!this.childList.contains(child)) {
/* 531:454 */       this.childList.add(child);
/* 532:    */     }
/* 533:    */   }
/* 534:    */   
/* 535:    */   @Transient
/* 536:    */   public List<WxContent> getChildList()
/* 537:    */   {
/* 538:460 */     return this.childList;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public boolean hasChild()
/* 542:    */   {
/* 543:464 */     return (this.childList != null) && (!this.childList.isEmpty());
/* 544:    */   }
/* 545:    */   
/* 546:    */   public boolean equals(Object obj)
/* 547:    */   {
/* 548:468 */     if (this == obj) {
/* 549:469 */       return true;
/* 550:    */     }
/* 551:471 */     if (obj == null) {
/* 552:472 */       return false;
/* 553:    */     }
/* 554:474 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 555:475 */       return false;
/* 556:    */     }
/* 557:477 */     WxContent other = (WxContent)obj;
/* 558:478 */     if (this.id == null)
/* 559:    */     {
/* 560:479 */       if (other.getId() != null) {
/* 561:480 */         return false;
/* 562:    */       }
/* 563:    */     }
/* 564:481 */     else if (!this.id.equals(other.getId())) {
/* 565:482 */       return false;
/* 566:    */     }
/* 567:484 */     return true;
/* 568:    */   }
/* 569:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.WxContent
 * JD-Core Version:    0.7.0.1
 */