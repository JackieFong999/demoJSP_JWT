<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List - jqGrid View</title>
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    <!-- jQuery UI -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/ui-lightness/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    
    <!-- jqGrid CSS from CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/css/ui.jqgrid.min.css">
    
    <!-- jqGrid JS from CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/jquery.jqgrid.min.js"></script>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
        }
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 10px 5px;
            cursor: pointer;
            border-radius: 4px;
        }
        .button:hover {
            background-color: #45a049;
        }
        .button-back {
            background-color: #2196F3;
        }
        .button-back:hover {
            background-color: #0b7dda;
        }
        .button-html {
            background-color: #ff9800;
        }
        .button-html:hover {
            background-color: #e68900;
        }
        .grid-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Product Management - jqGrid View</h1>
        
        <div style="margin-bottom: 15px;">
            <a href="${pageContext.request.contextPath}/home" class="button button-back">Back to Home</a>
            <a href="${pageContext.request.contextPath}/products" class="button button-html">View as HTML Table</a>
        </div>
        
        <div class="grid-container">
            <table id="productGrid"></table>
            <div id="productPager"></div>
        </div>
    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            $("#productGrid").jqGrid({
                url: '${pageContext.request.contextPath}/product/api/products',
                datatype: "json",
                mtype: "GET",
                colModel: [
                    { label: 'ID', name: 'id', width: 50, key: true, align: 'center' },
                    { label: 'Product Name', name: 'prod_name', width: 150 },
                    { label: 'Description', name: 'prod_desc', width: 300 },
                    { label: 'Price', name: 'prod_price', width: 100, align: 'right',
                      formatter: 'number', formatoptions: { decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2, prefix: "$ " } },
                    { label: 'Release Date', name: 'release_date', width: 150, align: 'center' }
                ],
                viewrecords: true,
                width: 1100,
                height: 400,
                rowNum: 10,
                rowList: [5, 10, 20, 30, 50],
                pager: "#productPager",
                caption: "Product List",
                jsonReader: {
                    root: "rows",
                    page: "page",
                    total: "total",
                    records: "records",
                    repeatitems: false,
                    id: "id"
                },
                loadError: function(xhr, status, error) {
                    console.error("Error loading data:", error);
                    alert("Error loading product data. Status: " + status);
                }
            }).jqGrid('navGrid', '#productPager', {
                edit: false,
                add: false,
                del: false,
                search: true,
                refresh: true
            });
        });
    </script>
</body>
</html>