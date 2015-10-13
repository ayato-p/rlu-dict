================
 逆引き Clojure
================

このプロジェクトは過去にあった同名サービスの置き換えです。

Requirements
============

開発には以下のものが必要です。

* Java 8
* Leiningen 2.5.2 or higher
* PostgreSQL 9.4.5 or higher

Developer's Guide
=================

設定
----

開発環境用の設定ファイルを作ります。

`/dev-resources/config-local.edn.default` を `/dev-resources/config-local.edn` とすることで開発環境下ではそれが使われます。

マイグレーション
----------------

.. sourcecode:: clojure

  $ lein migrate

サーバー起動
------------

.. sourcecode:: clojure

  $ lein repl

  (go)

License
=======

The MIT License (MIT)

Copyright (c) 2015 Ayato Nishimura, http://ayalog.com/

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
