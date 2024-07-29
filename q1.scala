case class Product(id: Int, name: String, quantity: Int, price: Double)

object InventorySystem {
  def retrieve(tempInventory: Map[Int, Product]): List[String] = {
    tempInventory.values.map(_.name).toList
  }

  def calculateValue(tempInventory: Map[Int, Product]): Double = {
    tempInventory.values.map(product => product.quantity * product.price).sum
  }

  def isempty(tempInventory: Map[Int, Product]): Boolean = {
    tempInventory.isEmpty
  }

  def mergeInventory(tempInventory1: Map[Int, Product], tempInventory2: Map[Int, Product]): Map[Int, Product] = {
    tempInventory2.foldLeft(tempInventory1) { case (acc, (id, product2)) =>
      acc.get(id) match {
        case Some(product1) =>
          val updatedProduct = Product(
            id = product1.id,
            name = product1.name,
            price = math.max(product1.price, product2.price),
            quantity = product1.quantity + product2.quantity
          )
          acc + (id -> updatedProduct)
        case None =>
          acc + (id -> product2)
      }
    }
  }

  def highestPrice(tempInventory: Map[Int, Product]): Double = {
    tempInventory.values.map(_.price).max
  }

  def checkProductInMap(tempInventory: Map[Int, Product], product: Product): Boolean = {
    tempInventory.contains(product.id)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val inventory1: Map[Int, Product] = Map(
      1 -> Product(1, "p1", 11, 30.0),
      2 -> Product(2, "p2", 5, 78.0),
      3 -> Product(3, "p3", 2, 12.0),
      4 -> Product(4, "p4", 17, 67.0)
    )

    val inventory2: Map[Int, Product] = Map(
      3 -> Product(3, "p3", 12, 34.0),
      2 -> Product(2, "p2", 44, 17.0)
    )

    val productNames1 = InventorySystem.retrieve(inventory1)
    println("List of Products in inventory1: " + productNames1)

    val tot1 = InventorySystem.calculateValue(inventory1)
    println("Total value is: " + tot1)

    if (InventorySystem.isempty(inventory1)) {
      println("Inventory 1 is empty")
    } else {
      println("Inventory 1 is not empty")
    }

    val mergedInventory = InventorySystem.mergeInventory(inventory1, inventory2)
    println("Merged Inventory: " + mergedInventory)

    val highestPrice = InventorySystem.highestPrice(mergedInventory)
    println(s"Highest price in merged inventory: $highestPrice")

    val productToCheck = Product(3, "p3", 12, 34.0)
    val isInMap = InventorySystem.checkProductInMap(mergedInventory, productToCheck)
    println(s"Product ID ${productToCheck.id} in merged inventory: $isInMap")
  }
}

