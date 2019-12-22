object PrintFactorization extends App {
  Seq(32, 25, 90, 510510).map(Factorization.factorization).map(println)
}