# キーワード
1. 型パラメータ
2. 共変 [+T]/反変[-T]/非変(invariant)
3. 上限境界(upper bounds)/下限境界(lower bounds)
4. 多相関数/単相関数
5. カリー化を使った型推論

# まとめ
- 型パラメータを利用するとクラスやメソッドを汎用的にできる
- 型パラメータを共変として指定することで、そのサブクラスでパラメータ化したものでも、同じ変数で扱える
- 型パラメータの境界を指定すると、利用できる型を制限したり、その型のメソッドを利用できる
- 型パラメータでより一般化され、複数の型で利用できる関数を多相関数という