<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List - AG Grid View</title>
    
    <!-- AG Grid CSS from CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.1/styles/ag-grid.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.1/styles/ag-theme-alpine.min.css">
    
    <!-- AG Grid JavaScript from CDN -->
    <script src="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.1/dist/ag-grid-community.min.js"></script>
    
    <!-- jQuery for AJAX -->
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1400px;
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
        .button-jqgrid {
            background-color: #9c27b0;
        }
        .button-jqgrid:hover {
            background-color: #7b1fa2;
        }
        .grid-container {
            margin-top: 20px;
            height: 600px;
            width: 100%;
        }
        .status-bar {
            margin-top: 10px;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 4px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Product Management - AG Grid View</h1>
        
        <div style="margin-bottom: 15px;">
            <a href="${pageContext.request.contextPath}/home" class="button button-back">Back to Home</a>
            <a href="${pageContext.request.contextPath}/products" class="button button-html">HTML Table</a>
            <a href="${pageContext.request.contextPath}/products-grid" class="button button-jqgrid">jqGrid View</a>
        </div>
        
        <div id="myGrid" class="ag-theme-alpine grid-container"></div>
        
        <div class="status-bar" id="statusBar">
            Loading data...
        </div>
    </div>

    <script type="text/javascript">
        let gridApi;
        
        // Define column definitions
        const columnDefs = [
            { 
                field: "id", 
                headerName: "ID", 
                width: 80,
                sortable: true,
                filter: "agNumberColumnFilter",
                checkboxSelection: true,
                headerCheckboxSelection: true
            },
            { 
                field: "prod_name", 
                headerName: "Product Name", 
                width: 180,
                sortable: true,
                filter: "agTextColumnFilter",
                resizable: true
            },
            { 
                field: "prod_desc", 
                headerName: "Description", 
                width: 350,
                sortable: true,
                filter: "agTextColumnFilter",
                resizable: true,
                wrapText: true,
                autoHeight: true
            },
            { 
                field: "prod_price", 
                headerName: "Price", 
                width: 120,
                sortable: true,
                filter: "agNumberColumnFilter",
                valueFormatter: (params) => {
                    return params.value ? '$ ' + Number(params.value).toFixed(2) : '$ 0.00';
                },
                cellStyle: { textAlign: 'right' }
            },
            { 
                field: "release_date", 
                headerName: "Release Date", 
                width: 190,
                sortable: true,
                filter: "agDateColumnFilter",
                valueFormatter: (params) => {
                    if (!params.value) return '';
                    const date = new Date(params.value);
                    return date.toLocaleDateString('en-US', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit'
                    });
                }
            }
        ];
        
        // Grid options
        const gridOptions = {
            columnDefs: columnDefs,
            rowModelType: 'clientSide',
            pagination: true,
            paginationPageSize: 20,
            paginationPageSizeSelector: [10, 20, 50, 100],
            domLayout: 'normal',
            enableCellTextSelection: true,
            ensureDomOrder: true,
            enableBrowserTooltips: true,
            tooltipShowDelay: 500,
            defaultColDef: {
                sortable: true,
                resizable: true,
                filter: true,
                floatingFilter: true
            },
            onGridReady: (event) => {
                console.log('Grid ready, loading data...');
                loadData();
            },
            onSelectionChanged: (event) => {
                const selectedRows = gridApi.getSelectedRows();
                updateStatusBar(selectedRows.length + ' row(s) selected');
            },
            onFilterChanged: (event) => {
                if (gridApi) {
                    const filteredRows = gridApi.getModel().getRowCount();
                    updateStatusBar(filteredRows + ' rows displayed (filtered)');
                }
            },
            onSortChanged: (event) => {
                if (gridApi) {
                    const totalRows = gridApi.getModel().getRowCount();
                    updateStatusBar(totalRows + ' rows displayed (sorted)');
                }
            }
        };
        
        // Function to load data from the server
        function loadData() {
            updateStatusBar('Loading data from server...');
            
            $.ajax({
                url: '${pageContext.request.contextPath}/product/api/products',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log('Data received:', response);
                    if (response && response.rows) {
                        const rowData = response.rows;
                        gridApi.setGridOption('rowData', rowData);
                        updateStatusBar('Loaded ' + rowData.length + ' products successfully');
                    } else {
                        console.error('Invalid response format:', response);
                        updateStatusBar('Error: Invalid data format received');
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error loading data:', error);
                    console.error('Status:', status);
                    console.error('Response:', xhr.responseText);
                    updateStatusBar('Error loading data: ' + error);
                }
            });
        }
        
        // Update status bar message
        function updateStatusBar(message) {
            const statusDiv = document.getElementById('statusBar');
            if (statusDiv) {
                statusDiv.innerHTML = message;
                setTimeout(() => {
                    if (gridApi) {
                        const totalRows = gridApi.getModel().getRowCount();
                        const now = new Date();
                        statusDiv.innerHTML = 'Total products: ' + totalRows + ' | ' + 
                            now.getFullYear() + '-' + 
                            String(now.getMonth() + 1).padStart(2, '0') + '-' + 
                            String(now.getDate()).padStart(2, '0') + ' ' +
                            String(now.getHours()).padStart(2, '0') + ':' +
                            String(now.getMinutes()).padStart(2, '0') + ':' +
                            String(now.getSeconds()).padStart(2, '0');
                    }
                }, 3000);
            }
        }
        
        // Initialize grid when page loads
        document.addEventListener('DOMContentLoaded', () => {
            const gridDiv = document.querySelector('#myGrid');
            gridApi = agGrid.createGrid(gridDiv, gridOptions);
        });
    </script>
</body>
</html>