package dbExamples

import scalikejdbc._

object Hooman{

  def hasDoge(name:String):Boolean = {
   implicit val session = AutoSession
    sql"""
         SELECT *
         FROM Hoomans
         WHERE name = $name AND EXISTS (
            SELECT * FROM Relations WHERE HoomanId = Hoomans.id
         )
       """.map(x => x).single().apply().nonEmpty
  }

  def getId(name:String):Option[Int] = {//None Some(4)
    implicit val session = AutoSession
    sql"""
         SELECT *
         FROM Hoomans
         WHERE name = $name
         """.map(rs => rs.int("id")).single().apply()
  }


  def addHooman(name:String) :Unit = {
    implicit val session = AutoSession
    //"); drop table Doges"
    sql"""insert into Hoomans(name) VALUES($name)""".update().apply()
  }

  def reqHooman(name: String):Option[Hooman] = {
    implicit val session = AutoSession
    sql"""select * from Hoomans WHERE name = $name"""
      .map(rs => Hooman(rs.int("id"), rs.string("name"))).single().apply()
  }

  def reqHoomans():Seq[Hooman] = {
    implicit val session = AutoSession
    sql"""select * from Hoomans"""
      .map(rs => Hooman(rs.int("id"), rs.string("name"))).list().apply()
  }
}


case class Hooman(id:Int, name:String) {
  def myDogs:Seq[Doge] = {
    implicit val session = AutoSession
    sql"""SELECT * FROM Doges
      WHERE EXISTS (
            SELECT * FROM Relations WHERE HoomanId = $id AND Doges.id = DogeId
         )""".map(rs => Doge(rs.int("id"), rs.string("name")))
      .list().apply()
  }
}
