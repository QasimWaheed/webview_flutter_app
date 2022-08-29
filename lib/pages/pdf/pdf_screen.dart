import 'dart:io';
import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_pdfviewer/pdfviewer.dart';

class PDFViewerScreen extends StatefulWidget {
  const PDFViewerScreen({Key? key, required this.url, required this.cookies})
      : super(key: key);

  final String url;
  final String cookies;

  @override
  State<PDFViewerScreen> createState() => _PDFViewerScreenState();
}

class _PDFViewerScreenState extends State<PDFViewerScreen> {
  late String fileName;

  @override
  void initState() {
    super.initState();
    fileName =
        (widget.url.substring(widget.url.lastIndexOf('/') + 1).split("."))
            .first;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(fileName),
      ),
      body: SfPdfViewer.network(
        widget.url,
        headers: {
          HttpHeaders.connectionHeader: 'keep-alive',
          HttpHeaders.cookieHeader: widget.cookies,
        },
      ),
    );
  }
}
