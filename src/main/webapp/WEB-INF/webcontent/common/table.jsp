<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>

<div class="modal fade bs-example-modal-lg in " aria-hidden="false"
	aria-labelledby="myLargeModalLabel" role="dialog" tabindex="-1"
	style="display: none; padding-right: 17px; width: 1200px">
	<div class="box">
		<div class="box-head modal-header">
			<button class="close" aria-label="Close" data-dismiss="modal"
				type="button">
				<span aria-hidden="true">¡Á</span>
			</button>
			<h3>Bug Details</h3>
		</div>
		<div class="box-content box-nomargin modal-body">
			<div class="tab-content ">
				<table id="MyGird"
					class='table table-striped dataTable table-bordered'>
					<col style="width: 13%" />
                    <col style="width: 8%" />
                    <col style="width: 7%" />
                    <col style="width: 9%" />
                    <col style="width: 10%" />
                    <col style="width: 19%" />
                    <col style="width: 14%" />
                    <col style="width: 10%" />
                    <col style="width: 10%" />
					<thead>
						<tr>
							<th>Key</th>
							<th>Domain</th>
							<th>Priority</th>
							<th>Status</th>
							<th>Create</th>
							<th>Assignee</th>
							<th>Reporter</th>
							<th>Severity</th>
							<th>Label</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>