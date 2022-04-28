object Http220VoltParseExample extends App {

  import org.jsoup._

  import scala.jdk.CollectionConverters._

  var totalSum = 0
  var totalSumNoSale = 0

  var cont = true
  var i = 0
  while (cont) {
    val doc = Jsoup.connect(s"https://www.220-volt.ru/catalog/perforatory/?p=$i").get

    val els = Option(doc.getElementById("product-list")).map(_.children().asScala.toSeq).getOrElse(Seq())
    for (ins <- els) {
      val name = ins.getElementsByClass("new-item-list-name").text()
      val priceOld: Option[Int] = ins.getElementsByClass("new-item-list-price-old").text().filter(_.isDigit).toIntOption
      val price: Option[Int] = ins.getElementsByClass("new-item-list-price-im").text().filter(_.isDigit).toIntOption
      totalSumNoSale += priceOld.orElse(price).getOrElse(0)
      totalSum += price.getOrElse(0)
      println(f"$name%-60s | ${priceOld.getOrElse("NO SALE")}%20s | ${price.getOrElse("NO PRICE")}%20s")
    }
    cont = els.nonEmpty
    i += 30
  }
  println(f"${"TOTAL"}%-60s | ${"Без скидки: " + totalSumNoSale}%20s | ${"всего со скидками: " + totalSum}%20s ")


}
