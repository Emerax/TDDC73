import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
  int backgroundIndex = new Random().nextInt(25) + 1;
  int cardSideIndex = 0;
  double companyOpacity = 0;
  String company = "visa";
  DateTime expiration = DateTime.now();
  TextEditingController cardNumberController = new TextEditingController();
  TextEditingController cardHolderController = new TextEditingController();
  TextEditingController cvvController = new TextEditingController();

  void initState() {
    super.initState();
    cardNumberController.addListener(updateCardNumber);
    cardHolderController.addListener(updateCardHolder);
    cvvController.addListener(updateCVV);
  }

  void dispose() {
    cardNumberController.dispose();
    super.dispose();
  }

  void updateCardNumber(){
    String strippedText = cardNumberController.text.replaceAll(new RegExp(r"[^0-9]"), "");
    if(strippedText.length > 16){
      strippedText = strippedText.substring(0, 16);
    }
    String newText = "";
    while(strippedText.length > 4){
      newText = "$newText${strippedText.substring(0, 4)} ";
      strippedText = strippedText.substring(4);
    }
    newText = "$newText$strippedText";

    setState(() {
      cardSideIndex = 0;
      cardNumberController.value = cardNumberController.value.copyWith(
        text: newText,
        selection: TextSelection(baseOffset: newText.length, extentOffset: newText.length),
        composing: TextRange.empty,
      );
      updateCompany();
    });
  }

  void updateCompany() {
    double newOpacity = 1;
    if(cardNumberController.text.length >= 2) {
      String companyIdentifier = cardNumberController.text.substring(0, 2);
      switch(companyIdentifier.substring(0, 1)){
        case "4":
          company = "visa";
          break;
        case "5":
          company = "mastercard";
          break;
        case "3":
          switch(companyIdentifier.substring(1, 2)){
            case "4":
            case "7":
              company = "amex";
              break;
            case "0":
            case "6":
            case "8":
              company = "dinersclub";
              break;
          }
          break;
        case "6":
          company = "discover";
          break;
        default:
          newOpacity = 0;
      }
    } else {
      newOpacity = 0;
    }
    companyOpacity = newOpacity;
  }

  void updateCardHolder() {
    String cardHolder = cardHolderController.text.toUpperCase();

    setState(() {
      cardSideIndex = 0;
      cardHolderController.value = cardHolderController.value.copyWith(
        text: cardHolder,
        selection: TextSelection.fromPosition(TextPosition(offset: cardHolderController.text.length)),
        composing: TextRange.empty,
      );
    });
  }

  void updateExpiration(int newMonth, int newYear) {
    setState(() {
      cardSideIndex = 0;
      expiration = new DateTime.utc(newYear, newMonth);
    });
  }

  void updateCVV(){
    String cvv = cvvController.text;
    //FIX: Hantera undantagsfallet i CVV-längd för AMEX-kort.
    if (company == "amex") {
      if (cvv.length > 4) {
        cvv = cvv.substring(0, 4);
      }
    } else {
      if(cvv.length > 3) {
        cvv = cvv.substring(0, 3);
      }
    }
    setState(() {
      cardSideIndex = 1;
      cvvController.value = cvvController.value.copyWith(
          text: cvv,
          composing: TextRange.empty,
          selection: TextSelection(baseOffset: cvv.length, extentOffset: cvv.length)
      );
    });
  }

  String formatExpiration(DateTime date){
    String month = "${expiration.month.toString().length == 2 ? expiration.month : "0" + expiration.month.toString()}";
    String year = "${date.year.toString().substring(2)}";
    return "$month/$year";
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
                                keyboardType: TextInputType.number,
                                inputFormatters: <TextInputFormatter>[
                                  FilteringTextInputFormatter.digitsOnly
                                ],
                                controller: cardNumberController,
                                decoration: const InputDecoration(
                                  labelText: "Card Number",
                                ),
                              ),
                              TextFormField(
                                controller: cardHolderController,
                                //FIX: Tillåt inga numeriska karaktärer i korthållarens namn.
                                inputFormatters: [
                                  FilteringTextInputFormatter.deny(
                                    RegExp("[0-9]")
                                  )
                                ],
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
                                            TextField(
                                              controller: cvvController,
                                              inputFormatters: <TextInputFormatter>[
                                                FilteringTextInputFormatter.digitsOnly
                                              ],
                                              keyboardType: TextInputType.number,
                                            ),
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
                                onPressed: () => {print("BUTTON")},
                              )
                            ],
                          ),
                        )
                    ),
                  ),
                  IndexedStack(
                    index: cardSideIndex,
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
                              image: AssetImage("images/$backgroundIndex.jpeg"),
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
                                  Opacity(
                                    opacity: companyOpacity,
                                    child: Image(
                                      image: AssetImage("images/$company.png"),
                                      fit: BoxFit.scaleDown,
                                      width: 60,
                                      height: 60,
                                    ),
                                  )
                                ],
                              ),
                              SizedBox(height: 20),
                              Container(
                                  decoration: BoxDecoration(
                                    //border: Border.all(color: Colors.white)
                                  ),
                                  child: Text(
                                    "${cardNumberController.text}",
                                    style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 24
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
                                          "${cardHolderController.text}",
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
                                          "${formatExpiration(expiration)}",
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
                        elevation: 10,
                        margin: EdgeInsets.all(50),
                        semanticContainer: true,
                        clipBehavior: Clip.antiAliasWithSaveLayer,
                        child: Container(
                          //padding: EdgeInsets.all(10),
                          decoration: BoxDecoration(
                              image: DecorationImage(
                                image: AssetImage("images/$backgroundIndex.jpeg"),
                                fit: BoxFit.fill,
                                alignment: Alignment.topCenter,
                              )
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: <Widget>[
                              SizedBox(
                                height: 20,
                              ),
                              Container( //Magnetic strip
                                width: 20,
                                height: 30,
                                color: Colors.black,
                              ),
                              Container(
                                padding: EdgeInsets.symmetric(
                                    horizontal: 10,
                                    vertical: 5
                                    ),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children: <Widget>[
                                    Text(
                                      "CVV",
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 9
                                      ),
                                    ),
                                    TextField(
                                      controller: cvvController,
                                      enabled: false,
                                      textDirection: TextDirection.rtl,
                                      decoration: InputDecoration(
                                        fillColor: Colors.white,
                                        filled: true,
                                      ),
                                      style: TextStyle(
                                        color: Colors.black
                                      ),
                                    ),
                                    Image(
                                      image: AssetImage("images/visa.png"),
                                      fit: BoxFit.scaleDown,
                                      width: 60,
                                      height: 60,
                                    )
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
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
