import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TDDC73 Lab 2',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'TDDC73 Lab2'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  DateTime expiration = DateTime.now();

  void updateExpiration(int newMonth, int newYear) {
    setState(() {
      expiration = new DateTime.utc(newYear, newMonth);
    });
    print("PRNT: Expiration date updated!\n Now: $expiration");
  }

  void toast(String message) {
    Scaffold.of(context).showSnackBar(SnackBar(
      content: Text(message),
      duration: Duration(seconds: 3),
    ));
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        child:
        SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Stack(
                alignment: Alignment.topCenter,
                children: <Widget>[
                  Padding(
                    padding: EdgeInsets.only(top: 120),
                    child: Card(
                        elevation: 10,
                        margin: EdgeInsets.all(20),
                        child: Container(
                          padding: EdgeInsets.fromLTRB(15, 100, 15, 15),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: <Widget>[
                              TextFormField(
                                decoration: const InputDecoration(
                                  labelText: "Card Number",
                                ),
                              ),
                              TextFormField(
                                decoration: const InputDecoration(
                                  labelText: "Card Holder",
                                ),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                                  Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: <Widget>[
                                      Text("Expiration Date"),
                                      ButtonBar(
                                        alignment: MainAxisAlignment.start,
                                        mainAxisSize: MainAxisSize.min,
                                        children: <Widget>[
                                          DropdownButton(
                                            hint: Text("Month"),
                                            value: expiration.month,
                                            items: <int>[
                                              1,
                                              2,
                                              3,
                                              4,
                                              5,
                                              6,
                                              7,
                                              8,
                                              9,
                                              10,
                                              11,
                                              12
                                            ].
                                            map<DropdownMenuItem<int>>((int value) {
                                              return DropdownMenuItem<int>(
                                                  value: value,
                                                  child: Text(value.toString()));
                                            }).toList(),
                                            onChanged: (int newMonth) {
                                              updateExpiration(newMonth, expiration.year);
                                            },
                                          ),
                                          DropdownButton(
                                            hint: Text("Year"),
                                            value: expiration.year,
                                            items: <int>[
                                              for (int i = DateTime.now().year;
                                              i <= DateTime.now().year + 21;
                                              ++i)
                                                i]
                                                .map<DropdownMenuItem<int>>((int value) {
                                              return DropdownMenuItem<int>(
                                                  value: value,
                                                  child: Text(value.toString()));
                                            }).toList(),
                                            onChanged: (int newYear) {
                                              updateExpiration(expiration.month, newYear);
                                            },
                                          )
                                        ],
                                      ),
                                    ],
                                  ),
                                  Expanded(
                                      child: Container(
                                        padding: EdgeInsets.fromLTRB(10, 0, 0, 0),
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: <Widget>[
                                            Text("CVV"),
                                            TextField(),
                                          ],
                                        ),
                                      )
                                  )
                                ],
                              ),
                              FlatButton(
                                color: Colors.blue,
                                textColor: Colors.white,
                                child: Text("Submit"),
                                onPressed: () => {toast("Message")},
                              )
                            ],
                          ),
                        )
                    ),
                  ),
                  IndexedStack(
                    index: 0,
                    children: <Widget>[
                      //Front of the credit card
                      Card(
                        elevation: 10,
                        margin: EdgeInsets.all(50),
                        semanticContainer: true,
                        clipBehavior: Clip.antiAliasWithSaveLayer,
                        child: Container(
                          padding: EdgeInsets.all(10),
                          decoration: BoxDecoration(
                            image: DecorationImage(
                              image: AssetImage("images/1.jpeg"),
                              fit: BoxFit.fill,
                              alignment: Alignment.topCenter,
                            )
                          ),
                          child: Column(
                            children: <Widget>[
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                                  Image(
                                    image: AssetImage("images/chip.png"),
                                    fit: BoxFit.scaleDown,
                                    width: 60,
                                    height: 60,
                                  ),
                                  Image(
                                    image: AssetImage("images/visa.png"),
                                    fit: BoxFit.scaleDown,
                                    width: 60,
                                    height: 60,
                                  )
                                ],
                              ),
                              SizedBox(height: 20),
                              Container(
                                  decoration: BoxDecoration(
                                    //border: Border.all(color: Colors.white)
                                  ),
                                  child: FittedBox(
                                    fit: BoxFit.fitWidth,
                                    child: Text(
                                      "1111 2222 3333 4444",
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 24
                                      ),
                                    ),
                                  )
                              ),
                              SizedBox(height: 20),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                                  Container(
                                    decoration: BoxDecoration(
                                      //border: Border.all(color: Colors.white),
                                    ),
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: <Widget>[
                                        Text(
                                          "Card Holder",
                                          style: TextStyle(
                                            color: Colors.grey,
                                            fontSize: 9
                                          ),
                                        ),
                                        Text(
                                          "Place Holder",
                                          style: TextStyle(
                                            color: Colors.white,
                                            fontSize: 18
                                          )
                                        )
                                      ],
                                    ),
                                  ),
                                  Container(
                                    decoration: BoxDecoration(
                                      //border: Border.all(color: Colors.white)
                                    ),
                                    child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: <Widget>[
                                      Text(
                                        "Expires",
                                        style: TextStyle(
                                            color: Colors.grey,
                                            fontSize: 9
                                        ),
                                      ),
                                      Text(
                                          "MM/YY",
                                          style: TextStyle(
                                              color: Colors.white,
                                              fontSize: 18
                                          )
                                      )
                                    ],
                                  ),
                                  )
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                      //Back of the credit card
                      Card(

                      )
                    ],
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
